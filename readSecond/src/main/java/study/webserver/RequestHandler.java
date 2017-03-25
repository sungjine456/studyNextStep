package study.webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.db.DataBase;
import study.model.User;
import study.util.HttpRequestUtils;
import study.util.IOUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream();
        		BufferedReader br = new BufferedReader(new InputStreamReader(in));) {
        	String line = br.readLine();
        	String url = getUrl(line);
        	System.out.println("url : " + url);
        	int contentLength = 0;
        	boolean isLogined = false;
            while(line != null && !"".equals(line)){
            	log.debug(line);
            	if(line.startsWith("Content-Length")){
            		contentLength = Integer.parseInt(HttpRequestUtils.parseHeader(line).getValue());
            	}
            	if(line.startsWith("Cookie")){
            		Map<String, String> params = HttpRequestUtils.parseCookies(line.split(": ")[1]);
            		if(params.containsKey("logined")){
            			isLogined = Boolean.valueOf(params.get("logined"));
            		}
            	}
            	line = br.readLine();
            }
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = makeBody("/index.html");
            if("/user/create".equals(url)){
            	DataBase.addUser(getUser(IOUtils.readData(br, contentLength)));
            	response302Header(dos, "/index.html");
            } else if("/user/login".equals(url)) {
            	User loginUser = getUser(IOUtils.readData(br, contentLength));
            	User findUser = DataBase.findUserById(loginUser.getUserId());
            	if(loginUser != null && findUser != null && findUser.getPassword().equals(loginUser.getPassword())){
            		resposnse200CookieHeader(dos, body.length, true);
            	} else {
            		body = makeBody("/user/login_failed.html");
            		resposnse200CookieHeader(dos, body.length, false);
            	}
            } else if("/user/list".equals(url)){
            	if(isLogined){
            		Collection<User> users = DataBase.findAll();
            		StringBuilder sb = new StringBuilder();
            		sb.append("<HTML><BODY><TABLE border=1>");
            		for(User user : users){
            			sb.append("<TR>");
            			sb.append("<TD>"+user.getUserId()+"</TD>");
            			sb.append("<TD>"+user.getEmail()+"</TD>");
            			sb.append("<TD>"+user.getName()+"</TD>");
            			sb.append("</TR>");
            		}
            		sb.append("</TABLE></BODY></HTML>");
            		body = sb.toString().getBytes();
            		response200Header(dos, body.length);
            	} else {
                	response302Header(dos, "/index.html");
            	}
        	} else {
        		body = makeBody(url);
            	response200Header(dos, body.length);
            }
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private void resposnse200CookieHeader(DataOutputStream dos, int lengthOfBodyContent, boolean isLoginSuccess) {
    	try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Set-Cookie: logined=" + isLoginSuccess + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
	private void response302Header(DataOutputStream dos, String url) {
		try {
			dos.writeBytes("HTTP/1.1 302 Found \r\n");
			dos.writeBytes("Location: " + url);
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private String getUrl(String line){
    	return line.split(" ")[1];
    }
    
    private byte[] makeBody(String url) throws IOException {
    	return Files.readAllBytes(new File("./webapp" + url).toPath());
    }
    
    private User getUser(String params){
    	Map<String, String> map = HttpRequestUtils.parseQueryString(params);
    	User user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
    	log.debug(user.toString());
    	return user;
    }
}

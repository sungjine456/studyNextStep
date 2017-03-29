package study.webserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream();) {
        	HttpRequest req = new HttpRequest(in);
        	
        	String url = req.getPath();
        	boolean isLogined = false;
        	if(req.getHeader("Cookie")!=null){
        		Map<String, String> params = HttpRequestUtils.parseCookies(req.getHeader("Cookie"));
        		if(params.containsKey("logined")){
        			isLogined = Boolean.parseBoolean(params.get("logined"));
        		}
        	}
        	
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = makeBody("/index.html");
            if("/user/create".equals(url)){
            	DataBase.addUser(getUser(req));
            	response302Header(dos, "/index.html");
            } else if("/user/login".equals(url)) {
            	User loginUser = getUser(req);
            	User findUser = DataBase.findUserById(loginUser.getUserId());
            	if(loginUser != null && findUser != null && findUser.getPassword().equals(loginUser.getPassword())){
            		resposnse302CookieHeader(dos, true, "/index.html");
            	} else {
            		body = makeBody("/user/login_failed.html");
            		resposnse302CookieHeader(dos, true, "/user/login_failed.html");
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
            		response302Header(dos, "/index.html");
            	} else {
                	response302Header(dos, "/index.html");
            	}
        	} else if(url.endsWith(".css")){
        		body = makeBody(url);
        		response200CssHeader(dos, body.length);
        	} else {
        		body = makeBody(url);
            	response200Header(dos, body.length);
            }
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private void response200CssHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private void resposnse302CookieHeader(DataOutputStream dos, boolean isLoginSuccess, String url) {
    	try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Set-Cookie: logined=" + isLoginSuccess + "\r\n");
            dos.writeBytes("Location: " + url);
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
    
    private byte[] makeBody(String url) throws IOException {
    	return Files.readAllBytes(new File("./webapp" + url).toPath());
    }
    
    private User getUser(HttpRequest req){
    	User user = new User(req.getParameter("userId"), req.getParameter("password"), 
    			req.getParameter("name"), req.getParameter("email"));
    	log.debug(user.toString());
    	return user;
    }
}

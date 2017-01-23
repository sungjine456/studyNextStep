package org.studyStepNext.part5.webserver;

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
import org.studyStepNext.part3.util.IOUtils;
import org.studyStepNext.part4.db.DataBase;
import org.studyStepNext.part4.model.User;
import org.studyStepNext.part4.util.HttpRequestUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
        	BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        	String line = br.readLine();
        	log.debug("request line : {}", line);
        	if(line == null){
        		return;
        	}
        	String[] tokens = line.split(" ");
        	boolean logined = false;
        	String url = tokens[1];
        	int contentLength = 0;
        	while(!"".equals(line)){
        		log.debug("header : {}", line);
        		line = br.readLine();
        		if(line.contains("Content-Length")){
        			contentLength = getContentLength(line);
        		}
        		if(line.contains("Cookie")){
        			logined = isLogin(line);
        		}
        	}
        	DataOutputStream dos = new DataOutputStream(out);
        	if("/user/create".equals(url)){
        		String body = IOUtils.readData(br, contentLength);
        		Map<String, String> params = HttpRequestUtils.parseQueryString(body);
        		User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        		log.debug("User : {}", user);
        		DataBase.addUser(user);
        		response302Header(dos, "/index.html");
        	} else if("/user/login".equals(url)){
        		String body = IOUtils.readData(br, contentLength);
        		Map<String, String> params = HttpRequestUtils.parseQueryString(body);
        		User user = DataBase.findUserById(params.get("userId"));
        		if(user != null && user.getPassword().equals(params.get("password"))){
        			response302LoginSuccessHeader(dos);
        		}
        		responseResource(dos, "/user/login_failed.html");
        	} else if("/user/list".equals(url)){
        		if(!logined){
        			responseResource(dos, url);
        			return;
        		}
        		Collection<User> users = DataBase.findAll();
        		StringBuilder sb = new StringBuilder();
        		sb.append("<table border='1'");
        		for(User user: users){
        			sb.append("<tr>");
        			sb.append("<td>" + user.getUserId() + "</td>");
        			sb.append("<td>" + user.getName() + "</td>");
        			sb.append("<td>" + user.getEmail() + "</td>");
        			sb.append("</tr>");
        		}
        		sb.append("</table>");
        		byte[] body = sb.toString().getBytes();
        		response200Header(dos, body.length);
                responseBody(dos, body);
        	} else if(url.endsWith(".css")){
            	byte[] body = Files.readAllBytes(new File("./WebContent" + url).toPath());
                response200CssHeader(dos, body.length);
                responseBody(dos, body);
        	}
    		responseResource(dos, url);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private boolean isLogin(String line){
    	String[] headerTokens = line.split(":");
    	Map<String, String> cookies = HttpRequestUtils.parseCookies(headerTokens[1].trim());
    	String value = cookies.get("logined");
    	if(value == null){
    		return false;
    	}
    	return Boolean.parseBoolean(value);
    }
    
    private void response302LoginSuccessHeader(DataOutputStream dos) {
    	try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Set-Cookie: logined=true \r\n");
            dos.writeBytes("Location: /index.html\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private void responseResource(DataOutputStream dos, String url) throws IOException {
    	byte[] body = Files.readAllBytes(new File("./WebContent" + url).toPath());
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private int getContentLength(String line) {
    	String[] headerTokens = line.split(":");
		return Integer.parseInt(headerTokens[1].trim());
	}
    
    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Location: " + url + "\r\n");
            dos.writeBytes("\r\n");
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

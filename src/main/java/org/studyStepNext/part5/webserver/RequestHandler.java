package org.studyStepNext.part5.webserver;

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
import org.studyStepNext.part5.db.DataBase;
import org.studyStepNext.part5.model.User;
import org.studyStepNext.part5.util.HttpRequestUtils;

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
        	HttpRequest req = new HttpRequest(in);
        	String path = getDefultPath(req.getPath());
        	DataOutputStream dos = new DataOutputStream(out);
        	if("/user/create".equals(path)){
        		User user = new User(
        				req.getParameter("userId"), 
        				req.getParameter("password"), 
        				req.getParameter("name"), 
        				req.getParameter("email"));
        		log.debug("User : {}", user);
        		DataBase.addUser(user);
        		response302Header(dos, "/index.html");
        	} else if("/user/login".equals(path)){
        		User user = DataBase.findUserById(req.getParameter("userId"));
        		if(user != null && user.getPassword().equals(req.getParameter("password"))){
        			response302LoginSuccessHeader(dos);
        		}
        		responseResource(dos, "/user/login_failed.html");
        	} else if("/user/list".equals(path)){
        		if(!isLogin(req.getHeader("Cookie"))){
        			responseResource(dos, path);
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
        	} else if(path.endsWith(".css")){
        		responseCssResource(dos, path);
        	}
    		responseResource(dos, path);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private String getDefultPath(String path){
    	if("/".equals(path)){
    		return "/index.html";
    	}
    	return path;
    }
    
    private boolean isLogin(String cookiValue){
    	Map<String, String> cookies = HttpRequestUtils.parseCookies(cookiValue);
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
    
    private void responseCssResource(DataOutputStream dos, String path) throws IOException {
    	byte[] body = Files.readAllBytes(new File("./WebContent" + path).toPath());
        response200CssHeader(dos, body.length);
        responseBody(dos, body);
    }
    
    private void responseResource(DataOutputStream dos, String path) throws IOException {
    	byte[] body = Files.readAllBytes(new File("./WebContent" + path).toPath());
        response200Header(dos, body.length);
        responseBody(dos, body);
    }
    
    private void response302Header(DataOutputStream dos, String path) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Location: " + path + "\r\n");
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

package org.studyStepNext.part5.webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
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
        	HttpResponse res = new HttpResponse(out);
        	String path = getDefultPath(req.getPath());
        	if("/user/create".equals(path)){
        		User user = new User(
        				req.getParameter("userId"), 
        				req.getParameter("password"), 
        				req.getParameter("name"), 
        				req.getParameter("email"));
        		log.debug("User : {}", user);
        		DataBase.addUser(user);
        		res.sendRedirect("/index.html");
        	} else if("/user/login".equals(path)){
        		User user = DataBase.findUserById(req.getParameter("userId"));
        		if(user != null && user.getPassword().equals(req.getParameter("password"))){
        			res.addHeader("SetCookie", "logined=true");
        			res.sendRedirect("/index.html");
        		} else {
        			res.sendRedirect("/user/login_failed.html");
        		}
        	} else if("/user/list".equals(path)){
        		if(!isLogin(req.getHeader("Cookie"))){
        			res.sendRedirect("/user/login.html");
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
        		res.forwardBody(sb.toString());
        	} else {
        		res.forward(path);
        	}
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
}

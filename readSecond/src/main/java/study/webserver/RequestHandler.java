package study.webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
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
        	HttpResponse res = new HttpResponse(out);
        	
        	String url = req.getPath();
        	boolean isLogined = false;
        	if(req.getHeader("Cookie")!=null){
        		Map<String, String> params = HttpRequestUtils.parseCookies(req.getHeader("Cookie"));
        		if(params.containsKey("logined")){
        			isLogined = Boolean.parseBoolean(params.get("logined"));
        		}
        	}
        	
            if("/user/create".equals(url)){
            	DataBase.addUser(getUser(req));
            	res.sendRedirect("/index.html");
            } else if("/user/login".equals(url)) {
            	User loginUser = getUser(req);
            	User findUser = DataBase.findUserById(loginUser.getUserId());
            	if(loginUser != null && findUser != null && findUser.getPassword().equals(loginUser.getPassword())){
            		res.addHeader("Set-Cookie", "logined=true");
            		res.sendRedirect("/index.html");
            	} else {
            		res.addHeader("Set-Cookie", "logined=false");
            		res.sendRedirect("/user/login_failed.html");
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
            		res.sendRedirect("/index.html");
            	} else {
            		res.sendRedirect("/index.html");
            	}
        	} else if(url.endsWith(".css")){
        		res.forward(url);
        	} else {
        		res.forward(url);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private User getUser(HttpRequest req){
    	User user = new User(req.getParameter("userId"), req.getParameter("password"), 
    			req.getParameter("name"), req.getParameter("email"));
    	log.debug(user.toString());
    	return user;
    }
}

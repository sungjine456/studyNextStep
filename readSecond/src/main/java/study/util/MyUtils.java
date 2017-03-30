package study.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.model.User;
import study.webserver.HttpRequest;

public class MyUtils {
	private static final Logger log = LoggerFactory.getLogger(MyUtils.class);
	
	public static User getUser(HttpRequest req){
    	User user = new User(req.getParameter("userId"), req.getParameter("password"), 
    			req.getParameter("name"), req.getParameter("email"));
    	log.debug(user.toString());
    	return user;
    }
}

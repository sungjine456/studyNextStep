package org.studyStepNext.part5.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part5.db.DataBase;
import org.studyStepNext.part5.model.User;
import org.studyStepNext.part5.util.HttpRequestUtils;
import org.studyStepNext.part5.webserver.HttpRequest;
import org.studyStepNext.part5.webserver.HttpResponse;

public class ListUserController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

	@Override
	public void doGet(HttpRequest req, HttpResponse res) {
		try{
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
		} catch(IOException e){
			log.debug(e.getMessage());
		}
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

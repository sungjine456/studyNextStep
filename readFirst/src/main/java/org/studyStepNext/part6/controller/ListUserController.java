package org.studyStepNext.part6.controller;

import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part6.db.DataBase;
import org.studyStepNext.part6.model.User;
import org.studyStepNext.part6.webserver.HttpRequest;
import org.studyStepNext.part6.webserver.HttpResponse;
import org.studyStepNext.part6.webserver.HttpSession;

public class ListUserController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

	@Override
	public void doGet(HttpRequest req, HttpResponse res) {
		try{
			if(!isLogin(req.getSession())){
				res.sendRedirect("/user/login.jsp");
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
	
	private boolean isLogin(HttpSession session){
		Object user = session.getAttribute("user");
    	if(user == null){
    		return false;
    	}
    	return true;
    }
}

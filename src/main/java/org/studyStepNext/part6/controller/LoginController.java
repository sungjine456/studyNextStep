package org.studyStepNext.part6.controller;

import org.studyStepNext.part6.db.DataBase;
import org.studyStepNext.part6.model.User;
import org.studyStepNext.part6.webserver.HttpRequest;
import org.studyStepNext.part6.webserver.HttpResponse;

public class LoginController extends AbstractController {

	@Override
	public void doPost(HttpRequest req, HttpResponse res) {
		User user = DataBase.findUserById(req.getParameter("userId"));
		if(user != null && user.getPassword().equals(req.getParameter("password"))){
			res.addHeader("SetCookie", "logined=true");
			res.sendRedirect("/index.html");
		} else {
			res.sendRedirect("/user/login_failed.html");
		}
	}
}

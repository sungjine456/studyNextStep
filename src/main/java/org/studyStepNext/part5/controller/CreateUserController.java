package org.studyStepNext.part5.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part5.db.DataBase;
import org.studyStepNext.part5.model.User;
import org.studyStepNext.part5.webserver.HttpRequest;
import org.studyStepNext.part5.webserver.HttpResponse;

public class CreateUserController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

	@Override
	public void doPost(HttpRequest req, HttpResponse res) {
		User user = new User(
				req.getParameter("userId"), 
				req.getParameter("password"), 
				req.getParameter("name"), 
				req.getParameter("email"));
		log.debug("User : {}", user);
		DataBase.addUser(user);
		res.sendRedirect("/index.html");
	}
}

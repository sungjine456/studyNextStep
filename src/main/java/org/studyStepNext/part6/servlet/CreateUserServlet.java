package org.studyStepNext.part6.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part6.db.DataBase;
import org.studyStepNext.part6.model.User;

@WebServlet("/user/create")
public class CreateUserServlet implements Controller {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		User user = new User(
				req.getParameter("userId"), 
				req.getParameter("password"), 
				req.getParameter("name"), 
				req.getParameter("email"));
		DataBase.addUser(user);
		return "/user/list";
	}
}

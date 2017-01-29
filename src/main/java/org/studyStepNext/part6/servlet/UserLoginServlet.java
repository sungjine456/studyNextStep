package org.studyStepNext.part6.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.studyStepNext.part6.db.DataBase;
import org.studyStepNext.part6.model.User;

@WebServlet("/user/login")
public class UserLoginServlet implements Controller {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = DataBase.findUserById(req.getParameter("userId"));
		if(user != null && user.getPassword().equals(req.getParameter("password"))){
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
		}
		return "/index.jsp";
	}
}

package org.studyStepNext.part6.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part6.db.DataBase;
import org.studyStepNext.part6.webserver.UserSessionUtils;

@WebServlet("/users")
public class ListUserServlet implements Controller {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		if(!UserSessionUtils.isLogined(req.getSession())){
			return "redirect:/users/loginForm";
		}
		req.setAttribute("users", DataBase.findAll());
		return "/user/list.jsp";
	}
}

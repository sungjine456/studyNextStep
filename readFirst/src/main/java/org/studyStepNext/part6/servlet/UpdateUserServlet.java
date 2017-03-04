package org.studyStepNext.part6.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.studyStepNext.part6.db.DataBase;
import org.studyStepNext.part6.model.User;

@WebServlet("/users/update")
public class UpdateUserServlet implements Controller {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String name = req.getParameter("name");
		if(user != null && userId.equals(user.getUserId())){
			DataBase.updateUser(new User(userId, password, name, email));
			return "redirect:/user/list";
		} else {
			return "redircet:/index.jsp";
		}
	}
}

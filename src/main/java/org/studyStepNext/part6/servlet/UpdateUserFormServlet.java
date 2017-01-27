package org.studyStepNext.part6.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.studyStepNext.part6.db.DataBase;
import org.studyStepNext.part6.model.User;

@WebServlet("/users/updateForm")
public class UpdateUserFormServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");
		String userId = req.getParameter("userId");
		if(user != null && userId.equals(user.getUserId())){
			req.setAttribute("user", DataBase.findUserById(userId));
			RequestDispatcher rd = req.getRequestDispatcher("/user/updateForm.jsp");
			rd.forward(req, resp);
		} else {
			resp.sendRedirect("/index.jsp");
		}
	}
}

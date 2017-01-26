package org.studyStepNext.part6.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part6.db.DataBase;

@WebServlet("/users/updateForm")
public class UpdateUserFormServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("user", DataBase.findUserById(req.getParameter("userId")));
		RequestDispatcher rd = req.getRequestDispatcher("/user/updateForm.jsp");
		rd.forward(req, resp);
	}
}

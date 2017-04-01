package study.next.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import study.core.db.DataBase;
import study.next.model.User;

@WebServlet("/updateUserForm")
public class UpdateUserFormServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
    	User user = (User)session.getAttribute("user");
    	if(user == null || user.getUserId().equals(req.getParameter("userId"))){
    		RequestDispatcher rd = req.getRequestDispatcher("/index.html");
            rd.forward(req, resp);
            return;
    	}
		req.setAttribute("user", DataBase.findUserById(req.getParameter("userId")));
		RequestDispatcher rd = req.getRequestDispatcher("/user/update.jsp");
        rd.forward(req, resp);
	}
}

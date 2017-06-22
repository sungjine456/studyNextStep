package study.next.web.user;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.Controller;
import study.core.mvc.JspView;
import study.core.mvc.View;
import study.next.dao.UserDao;
import study.next.model.User;

public class LoginController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	@Override
	public View execute(HttpServletRequest req, HttpServletResponse resp) {
		log.info("in login controller");
		UserDao userDao = new UserDao();
		try{
			User user = userDao.findByUserId(req.getParameter("userId"));
			if(user != null){
				log.info("success login");
				HttpSession session = req.getSession();
				session.setAttribute("user", user);
				req.setAttribute("users", userDao.findAll());
				return new JspView("redirect:/user/list");
			}
		}catch(SQLException e){
			log.error(e.getMessage());
		}
		return new JspView("redirect:/users/loginForm");
	}
}

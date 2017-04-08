package study.next.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.db.DataBase;
import study.core.mvc.Controller;
import study.next.model.User;

public class LoginController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		log.info("in login controller");
		User user = DataBase.findUserById(req.getParameter("userId"));
		if(user != null){
			log.info("success login");
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			req.setAttribute("users", DataBase.findAll());
			return "redirect:/user/list";
		}
		log.info("faild login");
		return "redirect:/users/loginForm";
	}
}

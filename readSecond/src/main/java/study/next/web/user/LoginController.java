package study.next.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.AbstractController;
import study.core.mvc.ModelAndView;
import study.next.dao.UserDao;
import study.next.model.User;

public class LoginController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	private UserDao userDao = UserDao.getInstance();
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) {
		log.info("in login controller");
		User user = userDao.findByUserId(req.getParameter("userId"));
		if(user != null){
			log.info("success login");
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			return jspView("redirect:/user/list").addObject("users", userDao.findAll());
		}
		return jspView("redirect:/users/loginForm");
	}
}

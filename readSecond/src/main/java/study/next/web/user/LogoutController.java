package study.next.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.Controller;

public class LogoutController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(LogoutController.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
    	log.info("logout controller");
		HttpSession session = req.getSession();
		session.removeAttribute("user");
		
		return "redirect:/user/list";
	}
}

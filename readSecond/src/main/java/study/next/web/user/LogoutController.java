package study.next.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.AbstractController;
import study.core.mvc.ModelAndView;

public class LogoutController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(LogoutController.class);
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) {
    	log.info("logout controller");
		HttpSession session = req.getSession();
		session.removeAttribute("user");
		
		return jspView("redirect:/user/list");
	}
}

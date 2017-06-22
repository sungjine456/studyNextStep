package study.next.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.Controller;
import study.core.mvc.JspView;
import study.core.mvc.View;

public class LogoutController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(LogoutController.class);
    @Override
    public View execute(HttpServletRequest req, HttpServletResponse resp) {
    	log.info("logout controller");
		HttpSession session = req.getSession();
		session.removeAttribute("user");
		
		return new JspView("redirect:/user/list");
	}
}

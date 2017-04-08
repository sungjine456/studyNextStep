package study.next.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.Controller;

public class HomeController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.info("HomeController");
		
		return "/index.jsp";
	}
}

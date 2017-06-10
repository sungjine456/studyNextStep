package study.next.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.Controller;
import study.next.dao.QuestionDao;

public class HomeController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	QuestionDao questionDao = new QuestionDao();
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.info("HomeController");
		req.setAttribute("questions", questionDao.findAll());
		return "/home.jsp";
	}
}

package study.next.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.AbstractController;
import study.core.mvc.ModelAndView;
import study.next.dao.QuestionDao;

public class HomeController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	QuestionDao questionDao = new QuestionDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
		log.info("HomeController");
		return jspView("/home.jsp").addObject("questions", questionDao.findAll());
	}
}

package study.next.web.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.Controller;
import study.core.mvc.JsonView;
import study.core.mvc.View;
import study.next.dao.AnswerDao;

public class DeleteAnswerController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(DeleteAnswerController.class);
	
	@Override
	public View execute(HttpServletRequest req, HttpServletResponse res) {
		long answerId = Long.parseLong(req.getParameter("answerId"));
		
		log.info("answerId : {}", answerId);
		
		AnswerDao answerDao = new AnswerDao();
		answerDao.delete(answerId);
		req.setAttribute("status", "true");
		return new JsonView();
	}
}

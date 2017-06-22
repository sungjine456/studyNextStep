package study.next.web.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.AbstractController;
import study.core.mvc.ModelAndView;
import study.next.dao.AnswerDao;
import study.next.model.Result;

public class DeleteAnswerController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(DeleteAnswerController.class);
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
		long answerId = Long.parseLong(req.getParameter("answerId"));
		
		log.info("answerId : {}", answerId);
		
		AnswerDao answerDao = new AnswerDao();
		answerDao.delete(answerId);
		return jsonView().addObject("result", Result.ok());
	}
}

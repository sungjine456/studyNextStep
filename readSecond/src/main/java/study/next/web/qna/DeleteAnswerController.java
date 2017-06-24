package study.next.web.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.AbstractController;
import study.core.mvc.ModelAndView;
import study.next.dao.AnswerDao;
import study.next.dao.QuestionDao;
import study.next.model.Result;

public class DeleteAnswerController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(DeleteAnswerController.class);
	private AnswerDao answerDao = AnswerDao.getInstance();
	private QuestionDao questionDao = QuestionDao.getInstance();
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
		long answerId = Long.parseLong(req.getParameter("answerId"));
		long questionId = answerDao.findByAnswerId(answerId).getQuestionId();
		
		log.info("answerId : {}", answerId);
		
		answerDao.delete(answerId);
		questionDao.decreaseCountOfAnswer(questionId);
		
		int countOfAnswers = questionDao.findByQuestionId(questionId).getCountOfAnswer();
		return jsonView().addObject("result", Result.ok()).addObject("countOfAnswer", countOfAnswers);
	}
}

package study.next.web.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.AbstractController;
import study.core.mvc.ModelAndView;
import study.next.dao.AnswerDao;
import study.next.dao.QuestionDao;
import study.next.model.Answer;

public class AddAnswerController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);
	
	private QuestionDao questionDao = QuestionDao.getInstance();
	private AnswerDao answerDao = AnswerDao.getInstance();
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
		long questionId = Long.parseLong(req.getParameter("questionId"));
		Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents")
								, questionId);
		
		log.debug("answer : {}", answer);
		
		Answer savedAnswer = answerDao.insert(answer);
		questionDao.increaseCountOfAnswer(questionId);
		int countOfAnswer = questionDao.findByQuestionId(questionId).getCountOfAnswer();
		return jsonView().addObject("answer", savedAnswer).addObject("countOfAnswer", countOfAnswer);
	}
}

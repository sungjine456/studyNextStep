package study.next.web.qna;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.AbstractController;
import study.core.mvc.ModelAndView;
import study.next.dao.AnswerDao;
import study.next.dao.QuestionDao;
import study.next.model.Answer;
import study.next.model.Question;
import study.next.model.Result;
import study.next.model.User;

public class ApiDeleteQuestionController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(ApiDeleteQuestionController.class);
	
	private QuestionDao questionDao = QuestionDao.getInstance();
	private AnswerDao answerDao = AnswerDao.getInstance();
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");
		if(user == null){
			return jsonView().addObject("result", Result.fail("로그인을 하세요."));
		}
		
		long questionId = Long.parseLong(req.getParameter("questionId"));
		Question question = questionDao.findByQuestionId(questionId);
		if(!user.equals(question.getWriter())){
			log.info("삭제하는 유저가 질문한 유저와 다르다.");
			return jsonView().addObject("result", Result.fail("삭제하는 유저가 질문한 유저와 다르다."));
		}
		
		List<Answer> answers = answerDao.findAllByQuestionId(questionId);
		long count = answers.stream().filter(answer -> !user.getName().equals(answer.getWriter())).count();
		if(count != 0){
			log.info("삭제하는 유저와 다른 답변한 유저가 있다.");
			return jsonView().addObject("result", Result.fail("삭제하는 유저와 다른 답변한 유저가 있다."));
		}
		
		questionDao.delete(questionId);
		return jsonView().addObject("result", Result.ok());
	}
}

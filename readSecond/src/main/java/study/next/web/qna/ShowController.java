package study.next.web.qna;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.Controller;
import study.next.dao.AnswerDao;
import study.next.dao.QuestionDao;
import study.next.model.Answer;
import study.next.model.Question;

public class ShowController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(ShowController.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
		
		if(session.getAttribute("user") == null){
			log.info("showController user session is null");
			return "redirect:/users/loginForm";
		}
		if(req.getParameter("questionId") == null){
			log.info("showController questionId is " + req.getParameter("questionId"));
			return "redirect:/";
		}
		QuestionDao questionDao = new QuestionDao();
		AnswerDao answerDao = new AnswerDao();
		long questionId = Long.parseLong(req.getParameter("questionId"));
		Question question = null;
		List<Answer> answers = null;
		try {
			question = questionDao.findByQuestionId(questionId);
			answers = answerDao.findAllByQuestionId(questionId);
		} catch (SQLException e){
			log.error(e.getMessage());
		}
		
		req.setAttribute("question", question);
		req.setAttribute("answers", answers);
		return "/qna/show.jsp";
	}
}
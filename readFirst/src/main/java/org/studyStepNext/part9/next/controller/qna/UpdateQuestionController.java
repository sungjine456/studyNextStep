package org.studyStepNext.part9.next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part9.core.mvc.AbstractController;
import org.studyStepNext.part9.core.mvc.ModelAndView;
import org.studyStepNext.part9.next.controller.UserSessionUtils;
import org.studyStepNext.part9.next.dao.QuestionDao;
import org.studyStepNext.part9.next.model.Question;
import org.studyStepNext.part9.next.model.User;

public class UpdateQuestionController extends AbstractController {
	private QuestionDao questionDao = QuestionDao.getInstance();
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if(!UserSessionUtils.isLogined(req.getSession())){
			return jspView("redirect:/users/loginForm");
		}
		long questionId = Long.parseLong(req.getParameter("questionId"));
		Question question = questionDao.findById(questionId);
		User user = UserSessionUtils.getUserFromSession(req.getSession());
		if(!question.getWriter().equals(user.getUserId())){
			throw new IllegalStateException();
		}
		Question newQuestion = new Question(question.getWriter(), req.getParameter("title"), req.getParameter("contents"));
		question.update(newQuestion);
		questionDao.update(question);
		return jspView("redirect:/");
	}
}

package org.studyStepNext.part9.next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part9.core.mvc.AbstractController;
import org.studyStepNext.part9.core.mvc.ModelAndView;
import org.studyStepNext.part9.next.controller.UserSessionUtils;
import org.studyStepNext.part9.next.dao.AnswerDao;
import org.studyStepNext.part9.next.dao.QuestionDao;
import org.studyStepNext.part9.next.model.Question;
import org.studyStepNext.part9.next.model.User;

public class DeleteQuestionController extends AbstractController {
	private QuestionDao questionDao = QuestionDao.getInstance();
	private AnswerDao answerDao = AnswerDao.getInstance();

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (!UserSessionUtils.isLogined(req.getSession())) {
			return jspView("redirect:/users/loginForm");
		}
		long questionId = Long.parseLong(req.getParameter("questionId"));
		User user = UserSessionUtils.getUserFromSession(req.getSession());
		Question question = questionDao.findById(questionId);
		if(question == null || (user.getUserId() != question.getWriter() && question.getCountOfComment() != 0)){
			return jspView("redirect:/");
		}
		questionDao.delete(questionId);
		return jspView("show.jsp").addObject("question", questionDao.findById(questionId))
                .addObject("answers", answerDao.findAllByQuestionId(questionId));
	}
}

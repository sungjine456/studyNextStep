package org.studyStepNext.part9.next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part9.core.mvc.AbstractController;
import org.studyStepNext.part9.core.mvc.ModelAndView;
import org.studyStepNext.part9.next.controller.UserSessionUtils;
import org.studyStepNext.part9.next.dao.QuestionDao;
import org.studyStepNext.part9.next.model.Question;
import org.studyStepNext.part9.next.model.User;

public class createQuestionController extends AbstractController {
	
	private QuestionDao questionDao = QuestionDao.getInstance();

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }
        User user = UserSessionUtils.getUserFromSession(req.getSession());
        Question question = new Question(user.getUserId(), req.getParameter("title"), req.getParameter("contents"));
        questionDao.insert(question);
        return jspView("redirect:/");
	}
}

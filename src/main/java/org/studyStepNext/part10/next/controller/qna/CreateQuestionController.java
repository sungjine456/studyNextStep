package org.studyStepNext.part10.next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part10.next.controller.UserSessionUtils;
import org.studyStepNext.part10.next.dao.QuestionDao;
import org.studyStepNext.part10.next.model.Question;
import org.studyStepNext.part10.next.model.User;
import org.studyStepNext.part10.core.mvc.AbstractController;
import org.studyStepNext.part10.core.mvc.ModelAndView;

public class CreateQuestionController extends AbstractController {
    private QuestionDao questionDao = QuestionDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }
        User user = UserSessionUtils.getUserFromSession(request.getSession());
        Question question = new Question(user.getUserId(), request.getParameter("title"),
                request.getParameter("contents"));
        questionDao.insert(question);
        return jspView("redirect:/");
    }

}

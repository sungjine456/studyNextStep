package org.studyStepNext.part10.next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part10.core.mvc.AbstractController;
import org.studyStepNext.part10.core.mvc.ModelAndView;
import org.studyStepNext.part10.next.controller.UserSessionUtils;

public class CreateFormQuestionController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }
        return jspView("/qna/form.jsp");
    }

}

package org.studyStepNext.part9.next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.studyStepNext.part9.core.mvc.AbstractController;
import org.studyStepNext.part9.core.mvc.ModelAndView;

public class LogoutController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return jspView("redirect:/qna/list");
    }
}

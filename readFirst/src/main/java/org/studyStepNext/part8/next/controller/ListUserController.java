package org.studyStepNext.part8.next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part8.core.mvc.AbstractController;
import org.studyStepNext.part8.core.mvc.ModelAndView;
import org.studyStepNext.part8.next.dao.UserDao;

public class ListUserController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }
        UserDao userDao = new UserDao();
        return jspView("/user/list.jsp").addObject("users", userDao.findAll());
    }
}

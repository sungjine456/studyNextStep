package org.studyStepNext.part8.next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part8.core.mvc.Controller;
import org.studyStepNext.part8.core.mvc.JspView;
import org.studyStepNext.part8.core.mvc.View;
import org.studyStepNext.part8.next.dao.UserDao;

public class HomeController implements Controller {
    @Override
    public View execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserDao userDao = new UserDao();
        req.setAttribute("users", userDao.findAll());
        return new JspView("home.jsp");
    }
}

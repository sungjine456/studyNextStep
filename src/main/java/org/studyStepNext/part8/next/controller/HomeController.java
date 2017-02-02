package org.studyStepNext.part8.next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part8.core.mvc.Controller;
import org.studyStepNext.part8.next.dao.UserDao;

public class HomeController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserDao userDao = new UserDao();
        req.setAttribute("users", userDao.findAll());
        return "home.jsp";
    }
}

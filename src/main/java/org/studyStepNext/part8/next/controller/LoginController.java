package org.studyStepNext.part8.next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.studyStepNext.part8.core.mvc.Controller;
import org.studyStepNext.part8.core.mvc.JspView;
import org.studyStepNext.part8.core.mvc.View;
import org.studyStepNext.part8.next.dao.UserDao;
import org.studyStepNext.part8.next.model.User;

public class LoginController implements Controller {
    @Override
    public View execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);
        if (user == null) {
            req.setAttribute("loginFailed", true);
            return new JspView("/user/login.jsp");
        }
        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return new JspView("redirect:/");
        } else {
            req.setAttribute("loginFailed", true);
            return new JspView("/user/login.jsp");
        }
    }
}

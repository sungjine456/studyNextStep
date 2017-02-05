package org.studyStepNext.part8.next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part8.core.mvc.Controller;
import org.studyStepNext.part8.core.mvc.JspView;
import org.studyStepNext.part8.core.mvc.View;
import org.studyStepNext.part8.next.dao.UserDao;
import org.studyStepNext.part8.next.model.User;

public class CreateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public View execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("User : {}", user);

        UserDao userDao = new UserDao();
        userDao.insert(user);
        return new JspView("redirect:/");
    }
}

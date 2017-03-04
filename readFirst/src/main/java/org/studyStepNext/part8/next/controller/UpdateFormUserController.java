package org.studyStepNext.part8.next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part8.core.mvc.AbstractController;
import org.studyStepNext.part8.core.mvc.ModelAndView;
import org.studyStepNext.part8.next.dao.UserDao;
import org.studyStepNext.part8.next.model.User;

public class UpdateFormUserController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        return jspView("/user/updateForm.jsp").addObject("user", user);
    }
}

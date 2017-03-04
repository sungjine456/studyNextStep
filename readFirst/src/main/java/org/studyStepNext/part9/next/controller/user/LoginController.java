package org.studyStepNext.part9.next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.studyStepNext.part9.next.dao.UserDao;
import org.studyStepNext.part9.next.model.User;
import org.studyStepNext.part9.core.mvc.AbstractController;
import org.studyStepNext.part9.core.mvc.ModelAndView;

public class LoginController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);

        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }

        if (user.matchPassword(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return jspView("redirect:/");
        } else {
            throw new IllegalStateException("비밀번호가 틀립니다.");
        }
    }
}

package org.studyStepNext.part8.next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part8.core.mvc.AbstractController;
import org.studyStepNext.part8.core.mvc.ModelAndView;
import org.studyStepNext.part8.next.dao.QuestionDao;

public class HomeController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return jspView("home.jsp").addObject("questions", questionDao.findAll());
    }
}

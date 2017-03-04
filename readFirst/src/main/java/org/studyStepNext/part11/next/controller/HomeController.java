package org.studyStepNext.part11.next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part11.core.mvc.AbstractController;
import org.studyStepNext.part11.core.mvc.ModelAndView;
import org.studyStepNext.part11.next.dao.QuestionDao;

public class HomeController extends AbstractController {
    private QuestionDao questionDao;

    public HomeController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jspView("home.jsp").addObject("questions", questionDao.findAll());
    }
}

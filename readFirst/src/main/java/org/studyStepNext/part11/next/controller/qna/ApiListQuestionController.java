package org.studyStepNext.part11.next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part11.core.mvc.AbstractController;
import org.studyStepNext.part11.core.mvc.ModelAndView;
import org.studyStepNext.part11.next.dao.QuestionDao;

public class ApiListQuestionController extends AbstractController {
    private QuestionDao questionDao;

    public ApiListQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return jsonView().addObject("questions", questionDao.findAll());
    }
}

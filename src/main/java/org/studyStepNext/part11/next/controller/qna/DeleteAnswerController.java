package org.studyStepNext.part11.next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part11.core.jdbc.DataAccessException;
import org.studyStepNext.part11.core.mvc.AbstractController;
import org.studyStepNext.part11.core.mvc.ModelAndView;
import org.studyStepNext.part11.next.dao.AnswerDao;
import org.studyStepNext.part11.next.model.Result;

public class DeleteAnswerController extends AbstractController {
    private AnswerDao answerDao;

    public DeleteAnswerController(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long answerId = Long.parseLong(request.getParameter("answerId"));

        ModelAndView mav = jsonView();
        try {
            answerDao.delete(answerId);
            mav.addObject("result", Result.ok());
        } catch (DataAccessException e) {
            mav.addObject("result", Result.fail(e.getMessage()));
        }
        return mav;
    }
}

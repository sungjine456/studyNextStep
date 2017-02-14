package org.studyStepNext.part11.next.controller.qna;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part11.core.mvc.AbstractController;
import org.studyStepNext.part11.core.mvc.ModelAndView;
import org.studyStepNext.part11.next.dao.AnswerDao;
import org.studyStepNext.part11.next.dao.QuestionDao;
import org.studyStepNext.part11.next.model.Answer;
import org.studyStepNext.part11.next.model.Question;

public class ShowQuestionController extends AbstractController {
    private QuestionDao questionDao;
    private AnswerDao answerDao;

    public ShowQuestionController(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        long questionId = Long.parseLong(req.getParameter("questionId"));

        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        ModelAndView mav = jspView("/qna/show.jsp");
        mav.addObject("question", question);
        mav.addObject("answers", answers);
        return mav;
    }
}

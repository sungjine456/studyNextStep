package org.studyStepNext.part10.next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part10.core.mvc.AbstractController;
import org.studyStepNext.part10.core.mvc.ModelAndView;
import org.studyStepNext.part10.next.controller.UserSessionUtils;
import org.studyStepNext.part10.next.dao.AnswerDao;
import org.studyStepNext.part10.next.dao.QuestionDao;
import org.studyStepNext.part10.next.model.Answer;
import org.studyStepNext.part10.next.model.Result;
import org.studyStepNext.part10.next.model.User;

public class AddAnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    private QuestionDao questionDao;
    private AnswerDao answerDao;
    
    public AddAnswerController(QuestionDao questionDao, AnswerDao answerDao) {
    	this.questionDao = questionDao;
    	this.answerDao = answerDao;
	}

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jsonView().addObject("result", Result.fail("Login is required"));
        }

        User user = UserSessionUtils.getUserFromSession(req.getSession());
        Answer answer = new Answer(user.getUserId(), req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId")));
        log.debug("answer : {}", answer);

        Answer savedAnswer = answerDao.insert(answer);
        questionDao.updateCountOfAnswer(savedAnswer.getQuestionId());

        return jsonView().addObject("answer", savedAnswer).addObject("result", Result.ok());
    }
}

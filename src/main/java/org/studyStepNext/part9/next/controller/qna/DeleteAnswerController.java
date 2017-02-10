package org.studyStepNext.part9.next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part9.core.mvc.AbstractController;
import org.studyStepNext.part9.core.mvc.ModelAndView;
import org.studyStepNext.part9.next.CannotDeleteException;
import org.studyStepNext.part9.next.controller.UserSessionUtils;
import org.studyStepNext.part9.next.dao.AnswerDao;
import org.studyStepNext.part9.next.dao.QuestionDao;
import org.studyStepNext.part9.next.service.QnaService;

public class DeleteAnswerController extends AbstractController {
	private QuestionDao questionDao = QuestionDao.getInstance();
	private AnswerDao answerDao = AnswerDao.getInstance();
    private QnaService qnaService = QnaService.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	if (!UserSessionUtils.isLogined(req.getSession())) {
			return jspView("redirect:/users/loginForm");
		}
        long questionId = Long.parseLong(req.getParameter("questionId"));
        try {
        	qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(req.getSession()));
            return jspView("redirect:/");
        } catch (CannotDeleteException e) {
            return jspView("show.jsp")
            		.addObject("question", questionDao.findById(questionId))
            		.addObject("answer", answerDao.findAllByQuestionId(questionId))
            		.addObject("errorMassege", e.getMessage());
        }
    }
}

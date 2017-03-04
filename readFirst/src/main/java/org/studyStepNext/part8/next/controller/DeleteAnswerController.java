package org.studyStepNext.part8.next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part8.core.mvc.AbstractController;
import org.studyStepNext.part8.core.mvc.ModelAndView;
import org.studyStepNext.part8.next.dao.AnswerDao;
import org.studyStepNext.part8.next.model.Result;

public class DeleteAnswerController extends AbstractController {
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int answerId = Integer.parseInt(req.getParameter("answerId"));
		AnswerDao answerDao = new AnswerDao();
		answerDao.delete(answerId);
		return jsonView().addObject("result", Result.ok());
	}
}

package org.studyStepNext.part8.next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part8.core.mvc.Controller;
import org.studyStepNext.part8.core.mvc.JsonView;
import org.studyStepNext.part8.core.mvc.View;
import org.studyStepNext.part8.next.dao.AnswerDao;
import org.studyStepNext.part8.next.model.Result;

public class DeleteAnswerController implements Controller {

	@Override
	public View execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int answerId = Integer.parseInt(req.getParameter("answerId"));
		AnswerDao answerDao = new AnswerDao();
		answerDao.delete(answerId);
		req.setAttribute("result", Result.ok());
		return new JsonView();
	}
}

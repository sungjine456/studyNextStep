package org.studyStepNext.part8.next.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part8.core.mvc.Controller;
import org.studyStepNext.part8.core.mvc.JsonView;
import org.studyStepNext.part8.core.mvc.View;
import org.studyStepNext.part8.next.dao.AnswerDao;
import org.studyStepNext.part8.next.model.Answer;

public class AddAnswerController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

	@Override
	public View execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"), 
				new Date(), Integer.parseInt(req.getParameter("questionId")));
		log.debug("answer : {}", answer.toString());
		
		AnswerDao answerDao = new AnswerDao();
		Answer savedAnswer = answerDao.insert(answer);
		req.setAttribute("answer", savedAnswer);
		return new JsonView();
	}
}

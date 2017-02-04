package org.studyStepNext.part8.next.controller;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part8.core.mvc.Controller;
import org.studyStepNext.part8.next.dao.AnswerDao;
import org.studyStepNext.part8.next.model.Answer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AddAnswerController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"), 
				new Date(), Integer.parseInt(req.getParameter("questionId")));
		log.debug("answer : {}", answer.toString());
		
		AnswerDao answerDao = new AnswerDao();
		Answer savedAnswer = answerDao.insert(answer);
		ObjectMapper mapper = new ObjectMapper();
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		pw.print(mapper.writeValueAsString(savedAnswer));
		return null;
	}
}

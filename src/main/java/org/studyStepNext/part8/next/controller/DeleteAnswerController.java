package org.studyStepNext.part8.next.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part8.core.mvc.Controller;
import org.studyStepNext.part8.next.dao.AnswerDao;
import org.studyStepNext.part8.next.model.Result;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DeleteAnswerController implements Controller {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int answerId = Integer.parseInt(req.getParameter("answerId"));
		AnswerDao answerDao = new AnswerDao();
		answerDao.delete(answerId);
		ObjectMapper mapper = new ObjectMapper();
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		pw.print(mapper.writeValueAsString(Result.ok()));
		return null;
	}
}

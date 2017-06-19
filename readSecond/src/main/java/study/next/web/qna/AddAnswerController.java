package study.next.web.qna;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import study.core.mvc.Controller;
import study.next.dao.AnswerDao;
import study.next.model.Answer;

public class AddAnswerController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents")
								, Long.parseLong(req.getParameter("questionId")));
		
		log.debug("answer : {}", answer);
		
		AnswerDao answerDao = new AnswerDao();
		Answer savedAnswer = answerDao.insert(answer);
		ObjectMapper mapper = new ObjectMapper();
		res.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out;
			out = res.getWriter();
			out.print(mapper.writeValueAsString(savedAnswer));
		} catch (JsonProcessingException e) {
			log.debug("message : {}", e.getMessage());
		} catch (IOException e) {
			log.debug("message : {}", e.getMessage());
		}
		return null;
	}
}

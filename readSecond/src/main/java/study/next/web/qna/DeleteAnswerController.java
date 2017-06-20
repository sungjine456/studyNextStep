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
import study.next.model.Result;

public class DeleteAnswerController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(DeleteAnswerController.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		long answerId = Long.parseLong(req.getParameter("answerId"));
		
		log.debug("answerId : {}" + answerId);
		
		AnswerDao answerDao = new AnswerDao();
		answerDao.delete(answerId);
		ObjectMapper mapper = new ObjectMapper();
		res.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out;
			out = res.getWriter();
			out.print(mapper.writeValueAsString(Result.ok()));
		} catch (JsonProcessingException e) {
			log.debug("message : {}", e.getMessage());
		} catch (IOException e) {
			log.debug("message : {}", e.getMessage());
		}
		return null;
	}
}

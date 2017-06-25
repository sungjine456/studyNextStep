package study.next.web.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.AbstractController;
import study.core.mvc.ModelAndView;
import study.next.exception.CannotDeleteException;
import study.next.model.User;
import study.next.service.QuestionService;

public class DeleteQuestionController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(DeleteQuestionController.class);
	
	private QuestionService questionService = QuestionService.getInstance();
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");
		if(user == null){
			return jspView("redirect:/users/loginForm");
		}
		
		long questionId = Long.parseLong(req.getParameter("questionId"));
		
		try {
			questionService.deleteQuestion(questionId, user);
		} catch(CannotDeleteException cde) {
			log.info(cde.getMessage());
			return jspView("redirect:/qna/show?questionId="+questionId);
		}
		
		return jspView("redirect:/");
	}
}

package study.next.web.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import study.core.mvc.AbstractController;
import study.core.mvc.ModelAndView;
import study.next.dao.QuestionDao;
import study.next.model.Question;
import study.next.model.User;

public class UpdateFormQuestionController extends AbstractController {

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");
		
		long questionId = Long.parseLong(req.getParameter("questionId"));
		
		QuestionDao questionDao = new QuestionDao();
		Question question = questionDao.findByQuestionId(questionId);
		
		if(!question.getWriter().equals(user)){
			return jspView("redirect:/");
		}
		
		return jspView("/qna/update.jsp").addObject("question", question);
	}
}

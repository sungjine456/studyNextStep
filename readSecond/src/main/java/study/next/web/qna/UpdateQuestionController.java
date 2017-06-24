package study.next.web.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import study.core.mvc.AbstractController;
import study.core.mvc.ModelAndView;
import study.next.dao.QuestionDao;
import study.next.model.Question;
import study.next.model.User;

public class UpdateQuestionController extends AbstractController {

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");
		QuestionDao questionDao = new QuestionDao();
		Question question = questionDao.findByQuestionId(Long.parseLong(req.getParameter("questionId")));
		if(!user.equals(question.getWriter())){
			return jspView("redirect:/");
		}
		question.update(req.getParameter("title"), req.getParameter("contents"));
		
		questionDao.update(question);
		return jspView("redirect:/");
	}
}

package study.next.web.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.AbstractController;
import study.core.mvc.ModelAndView;
import study.next.dao.QuestionDao;
import study.next.model.Question;
import study.next.model.User;

public class AddQuestionController extends AbstractController {
	private Logger log = LoggerFactory.getLogger(AddQuestionController.class);

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
		log.info("in AddQuestionController");
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");
		if(user == null){
			return jspView("redirect:/users/loginForm");
		}
		QuestionDao questionDao = new QuestionDao();
		questionDao.insert(new Question(user.getUserId(), req.getParameter("title"), req.getParameter("contents")));
		
		return jspView("redirect:/");
	}
}

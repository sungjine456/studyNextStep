package study.next.web.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.AbstractController;
import study.core.mvc.ModelAndView;
import study.next.dao.QuestionDao;
import study.next.dao.UserDao;
import study.next.model.Question;
import study.next.model.User;

public class AddQuestionController extends AbstractController {
	private Logger log = LoggerFactory.getLogger(AddQuestionController.class);
	
	private QuestionDao questionDao = QuestionDao.getInstance();
	private UserDao userDao = UserDao.getInstance();

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
		log.info("in AddQuestionController");
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");
		if(user == null){
			return jspView("redirect:/users/loginForm");
		}
		questionDao.insert(new Question(userDao.findByUserId(user.getUserId()), req.getParameter("title"), req.getParameter("contents")));
		
		return jspView("redirect:/");
	}
}

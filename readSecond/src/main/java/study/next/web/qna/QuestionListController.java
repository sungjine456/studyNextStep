package study.next.web.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import study.core.mvc.AbstractController;
import study.core.mvc.ModelAndView;
import study.next.dao.QuestionDao;

public class QuestionListController extends AbstractController {
	
	private QuestionDao questionDao = new QuestionDao();
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
		return jsonView().addObject("questionList", questionDao.findAll());
	}
}

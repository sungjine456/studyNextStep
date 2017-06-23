package study.next.web.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import study.core.mvc.AbstractController;
import study.core.mvc.ModelAndView;

public class FormController extends AbstractController {
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
		
		if(session.getAttribute("user") == null){
			return jspView("redirect:/users/loginForm");
		}
		return jspView("/qna/form.jsp");
	}
}

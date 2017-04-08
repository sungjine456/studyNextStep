package study.next.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.db.DataBase;
import study.core.mvc.Controller;
import study.next.model.User;

public class UpdateUserFormController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(UpdateUserFormController.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
    	User user = (User)session.getAttribute("user");
    	if(user == null || user.getUserId().equals(req.getParameter("userId"))){
    		log.info("empty user session");
    		return "redirect:/";
    	}
    	log.info("is user session");
		req.setAttribute("user", DataBase.findUserById(req.getParameter("userId")));
		return "/user/update.jsp";
	}
}

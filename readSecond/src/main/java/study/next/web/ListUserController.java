package study.next.web;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.Controller;
import study.next.dao.UserDao;
import study.next.model.User;

public class ListUserController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(ListUserController.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
    	HttpSession session = req.getSession();
    	User user = (User)session.getAttribute("user");
    	if(user == null){
    		log.info("User null");
    		return "redirect:/";
    	}
    	log.info("User not null");
    	UserDao userDao = new UserDao();
        try {
			req.setAttribute("users", userDao.findAll());
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
        return "/user/list.jsp";
    }
}
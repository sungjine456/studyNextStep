package study.next.web.user;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.Controller;
import study.next.dao.UserDao;
import study.next.model.User;

public class UpdateUserController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
    	User user = (User)session.getAttribute("user");
    	if(user == null || user.getUserId().equals(req.getParameter("userId"))){
    		log.info("empty user session");
            return "rediect:/";
    	}
    	log.info("is user session");
		User updateUser = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
		UserDao userDao = new UserDao();
		try {
			userDao.update(updateUser);
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return "redirect:/user/list";
	}
}

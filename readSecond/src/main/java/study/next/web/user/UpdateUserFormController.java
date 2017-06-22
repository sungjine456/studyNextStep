package study.next.web.user;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.AbstractController;
import study.core.mvc.ModelAndView;
import study.next.dao.UserDao;
import study.next.model.User;

public class UpdateUserFormController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(UpdateUserFormController.class);
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
    	User user = (User)session.getAttribute("user");
    	if(user == null || user.getUserId().equals(req.getParameter("userId"))){
    		log.info("empty user session");
    		return jspView("redirect:/");
    	}
    	log.info("is user session");
    	UserDao userDao = new UserDao();
    	User findUser = null;
    	try {
			findUser = userDao.findByUserId(req.getParameter("userId"));
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return jspView("/user/update.jsp").addObject("user", findUser);
	}
}

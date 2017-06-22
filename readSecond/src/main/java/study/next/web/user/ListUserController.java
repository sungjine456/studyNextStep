package study.next.web.user;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.core.mvc.AbstractController;
import study.core.mvc.ModelAndView;
import study.next.dao.UserDao;
import study.next.model.User;

public class ListUserController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(ListUserController.class);
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) {
    	HttpSession session = req.getSession();
    	User user = (User)session.getAttribute("user");
    	if(user == null){
    		log.info("User null");
    		return jspView("redirect:/");
    	}
    	log.info("User not null");
    	UserDao userDao = new UserDao();
    	List<User> userList = null;
        try {
        	userList = userDao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return jspView("/user/list.jsp").addObject("users", userList);
    }
}

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

public class CreateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.info("user : {}", user);
        
        UserDao userDao = new UserDao();
        try{
        	userDao.insert(user);
        } catch(SQLException e){
        	log.error(e.getMessage());
        }

        HttpSession session = req.getSession();
		session.setAttribute("user", user);
		
        return "redirect:/";
    }
}

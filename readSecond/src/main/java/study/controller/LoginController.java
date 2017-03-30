package study.controller;

import study.db.DataBase;
import study.model.User;
import study.util.MyUtils;
import study.webserver.HttpRequest;
import study.webserver.HttpResponse;

public class LoginController extends AbstractController {
	@Override
	public void doPost(HttpRequest req, HttpResponse res) {
		User loginUser = MyUtils.getUser(req);
    	User findUser = DataBase.findUserById(loginUser.getUserId());
    	if(loginUser != null && findUser != null && findUser.getPassword().equals(loginUser.getPassword())){
    		res.addHeader("Set-Cookie", "logined=true");
    		res.sendRedirect("/index.html");
    	} else {
    		res.addHeader("Set-Cookie", "logined=false");
    		res.sendRedirect("/user/login_failed.html");
    	}
	}
}

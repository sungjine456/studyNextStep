package study.controller;

import study.db.DataBase;
import study.util.MyUtils;
import study.webserver.HttpRequest;
import study.webserver.HttpResponse;

public class CreateUserController extends AbstractController {
	@Override
	public void doPost(HttpRequest req, HttpResponse res) {
		DataBase.addUser(MyUtils.getUser(req));
    	res.sendRedirect("/index.html");
	}
}

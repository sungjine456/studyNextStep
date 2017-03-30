package study.controller;

import java.util.Collection;
import java.util.Map;

import study.db.DataBase;
import study.model.User;
import study.util.HttpRequestUtils;
import study.webserver.HttpRequest;
import study.webserver.HttpResponse;

public class ListUserController extends AbstractController {
	@Override
	public void doGet(HttpRequest req, HttpResponse res) {
		boolean isLogined = false;
    	if(req.getHeader("Cookie")!=null){
    		Map<String, String> params = HttpRequestUtils.parseCookies(req.getHeader("Cookie"));
    		if(params.containsKey("logined")){
    			isLogined = isLogin(params.get("logined"));
    		}
    	}
    	System.out.println(isLogined);
		if(isLogined){
    		Collection<User> users = DataBase.findAll();
    		StringBuilder sb = new StringBuilder();
    		sb.append("<HTML><BODY><TABLE border=1>");
    		for(User user : users){
    			sb.append("<TR>");
    			sb.append("<TD>"+user.getUserId()+"</TD>");
    			sb.append("<TD>"+user.getEmail()+"</TD>");
    			sb.append("<TD>"+user.getName()+"</TD>");
    			sb.append("</TR>");
    		}
    		sb.append("</TABLE></BODY></HTML>");
    		res.forward(sb.toString().getBytes());
    	} else {
    		res.sendRedirect("/index.html");
    	}
	}
	
	private boolean isLogin(String logined){
		return Boolean.parseBoolean(logined);
	}
}

package study.core.mvc;

import java.util.HashMap;
import java.util.Map;

import study.next.web.CreateUserController;
import study.next.web.HomeController;
import study.next.web.ListUserController;
import study.next.web.LoginController;
import study.next.web.LogoutController;
import study.next.web.UpdateUserController;
import study.next.web.UpdateUserFormController;

public class RequestMapping {
	private Map<String, Controller> mappingUrl = new HashMap<>();
	void initMapping(){
		mappingUrl.put("/", new HomeController());
		mappingUrl.put("/user/form", new ForwardController("/user/form.jsp"));
		mappingUrl.put("/users/loginForm", new ForwardController("/user/login_failed.jsp"));
		mappingUrl.put("/user/login", new LoginController());
		mappingUrl.put("/user/logout", new LogoutController());
		mappingUrl.put("/user/list", new ListUserController());
		mappingUrl.put("/user/create", new CreateUserController());
		mappingUrl.put("/users/update", new UpdateUserController());
		mappingUrl.put("/updateUserForm", new UpdateUserFormController());
	}
	
	public Controller findController(String url){
		return mappingUrl.get(url);
	}
	
	void put(String url, Controller controller){
		mappingUrl.put(url, controller);
	}
}

package study.webserver;

import java.util.HashMap;
import java.util.Map;

import study.controller.Controller;
import study.controller.CreateUserController;
import study.controller.ListUserController;
import study.controller.LoginController;

public class RequestMapping {
	private static Map<String, Controller> controllerMap = new HashMap<>();
	
	static {
		controllerMap.put("/user/create", new CreateUserController());
    	controllerMap.put("/user/login", new LoginController());
    	controllerMap.put("/user/list", new ListUserController());
	}
	
	public static Controller getControllerMap(String url){
		return controllerMap.get(url);
	}
}

package org.studyStepNext.part5.webserver;

import java.util.HashMap;
import java.util.Map;

import org.studyStepNext.part5.controller.Controller;
import org.studyStepNext.part5.controller.CreateUserController;
import org.studyStepNext.part5.controller.ListUserController;
import org.studyStepNext.part5.controller.LoginController;

public class RequestMapping {
	private static Map<String, Controller> controllers = new HashMap<String, Controller>(); 
	
	static {
		controllers.put("/user/create", new CreateUserController());
		controllers.put("/user/login", new LoginController());
		controllers.put("/user/list", new ListUserController());
	}
	
	public static Controller getController(String requestUrl){
		return controllers.get(requestUrl);
	}
}

package org.studyStepNext.part6.webserver;

import java.util.HashMap;
import java.util.Map;

import org.studyStepNext.part6.servlet.Controller;
import org.studyStepNext.part6.servlet.CreateUserServlet;
import org.studyStepNext.part6.servlet.ListUserServlet;
import org.studyStepNext.part6.servlet.UpdateUserFormServlet;
import org.studyStepNext.part6.servlet.UpdateUserServlet;
import org.studyStepNext.part6.servlet.UserLoginServlet;
import org.studyStepNext.part6.servlet.UserLogoutServlet;

public class RequestMapping {
	private static Map<String, Controller> controllers = new HashMap<String, Controller>(); 
	
	public void initMapping() {
		controllers.put("/user/create", new CreateUserServlet());
		controllers.put("/user/login", new UserLoginServlet());
		controllers.put("/user/list", new ListUserServlet());
		controllers.put("/user/logout", new UserLogoutServlet());
		controllers.put("/users/updateForm", new UpdateUserFormServlet());
		controllers.put("/users/update", new UpdateUserServlet());
	}
	
	public Controller getController(String requestUrl){
		return controllers.get(requestUrl);
	}
	void put(String url, Controller controller){
		controllers.put(url, controller);
	}
}

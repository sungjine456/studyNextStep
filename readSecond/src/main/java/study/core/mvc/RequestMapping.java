package study.core.mvc;

import java.util.HashMap;
import java.util.Map;

import study.next.web.HomeController;
import study.next.web.qna.AddAnswerController;
import study.next.web.qna.AddQuestionController;
import study.next.web.qna.DeleteAnswerController;
import study.next.web.qna.FormController;
import study.next.web.qna.QuestionListController;
import study.next.web.qna.ShowController;
import study.next.web.qna.UpdateFormQuestionController;
import study.next.web.qna.UpdateQuestionController;
import study.next.web.user.CreateUserController;
import study.next.web.user.ListUserController;
import study.next.web.user.LoginController;
import study.next.web.user.LogoutController;
import study.next.web.user.UpdateUserController;
import study.next.web.user.UpdateUserFormController;

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
		mappingUrl.put("/users/updateForm", new UpdateUserFormController());
		mappingUrl.put("/qna/show", new ShowController());
		mappingUrl.put("/qna/form", new FormController());
		mappingUrl.put("/qna/create", new AddQuestionController());
		mappingUrl.put("/api/qna/addAnswer", new AddAnswerController());
		mappingUrl.put("/api/qna/deleteAnswer", new DeleteAnswerController());
		mappingUrl.put("/qna/update", new UpdateQuestionController());
		mappingUrl.put("/qna/updateForm", new UpdateFormQuestionController());
		mappingUrl.put("/api/qna/list", new QuestionListController());
	}
	
	public Controller findController(String url){
		return mappingUrl.get(url);
	}
	
	void put(String url, Controller controller){
		mappingUrl.put(url, controller);
	}
}

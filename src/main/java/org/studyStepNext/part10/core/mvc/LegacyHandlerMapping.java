package org.studyStepNext.part10.core.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part10.core.nmvc.HandlerMapping;
import org.studyStepNext.part10.next.controller.HomeController;
import org.studyStepNext.part10.next.controller.qna.AddAnswerController;
import org.studyStepNext.part10.next.controller.qna.ApiDeleteQuestionController;
import org.studyStepNext.part10.next.controller.qna.ApiListQuestionController;
import org.studyStepNext.part10.next.controller.qna.CreateFormQuestionController;
import org.studyStepNext.part10.next.controller.qna.CreateQuestionController;
import org.studyStepNext.part10.next.controller.qna.DeleteAnswerController;
import org.studyStepNext.part10.next.controller.qna.DeleteQuestionController;
import org.studyStepNext.part10.next.controller.qna.ShowQuestionController;
import org.studyStepNext.part10.next.controller.qna.UpdateFormQuestionController;
import org.studyStepNext.part10.next.controller.qna.UpdateQuestionController;
import org.studyStepNext.part10.next.controller.user.CreateUserController;
import org.studyStepNext.part10.next.controller.user.ListUserController;
import org.studyStepNext.part10.next.controller.user.LoginController;
import org.studyStepNext.part10.next.controller.user.LogoutController;
import org.studyStepNext.part10.next.controller.user.ProfileController;
import org.studyStepNext.part10.next.controller.user.UpdateFormUserController;
import org.studyStepNext.part10.next.controller.user.UpdateUserController;

public class LegacyHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        mappings.put("/", new HomeController());
        mappings.put("/users/form", new ForwardController("/user/form.jsp"));
        mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        mappings.put("/users", new ListUserController());
        mappings.put("/users/login", new LoginController());
        mappings.put("/users/profile", new ProfileController());
        mappings.put("/users/logout", new LogoutController());
        mappings.put("/users/create", new CreateUserController());
        mappings.put("/users/updateForm", new UpdateFormUserController());
        mappings.put("/users/update", new UpdateUserController());
        mappings.put("/qna/show", new ShowQuestionController());
        mappings.put("/qna/form", new CreateFormQuestionController());
        mappings.put("/qna/create", new CreateQuestionController());
        mappings.put("/qna/updateForm", new UpdateFormQuestionController());
        mappings.put("/qna/update", new UpdateQuestionController());
        mappings.put("/qna/delete", new DeleteQuestionController());
        mappings.put("/api/qna/deleteQuestion", new ApiDeleteQuestionController());
        mappings.put("/api/qna/list", new ApiListQuestionController());
        mappings.put("/api/qna/addAnswer", new AddAnswerController());
        mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController());

        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }

	@Override
	public Object getHandler(HttpServletRequest req) {
		return mappings.get(req.getRequestURI());
	}
}

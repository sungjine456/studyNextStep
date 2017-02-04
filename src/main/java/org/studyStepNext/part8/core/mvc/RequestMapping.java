package org.studyStepNext.part8.core.mvc;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part8.next.controller.AddAnswerController;
import org.studyStepNext.part8.next.controller.CreateUserController;
import org.studyStepNext.part8.next.controller.HomeController;
import org.studyStepNext.part8.next.controller.ListUserController;
import org.studyStepNext.part8.next.controller.LoginController;
import org.studyStepNext.part8.next.controller.LogoutController;
import org.studyStepNext.part8.next.controller.ProfileController;
import org.studyStepNext.part8.next.controller.UpdateFormUserController;
import org.studyStepNext.part8.next.controller.UpdateUserController;

public class RequestMapping {
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
        mappings.put("/api/qna/addAnswer", new AddAnswerController());

        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }
}

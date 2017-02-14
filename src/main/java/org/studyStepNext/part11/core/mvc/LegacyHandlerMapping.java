package org.studyStepNext.part11.core.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part11.next.controller.qna.AddAnswerController;
import org.studyStepNext.part11.core.nmvc.DispatcherServlet;
import org.studyStepNext.part11.core.nmvc.HandlerMapping;
import org.studyStepNext.part11.next.controller.HomeController;
import org.studyStepNext.part11.next.controller.qna.ApiDeleteQuestionController;
import org.studyStepNext.part11.next.controller.qna.ApiListQuestionController;
import org.studyStepNext.part11.next.controller.qna.CreateFormQuestionController;
import org.studyStepNext.part11.next.controller.qna.CreateQuestionController;
import org.studyStepNext.part11.next.controller.qna.DeleteAnswerController;
import org.studyStepNext.part11.next.controller.qna.DeleteQuestionController;
import org.studyStepNext.part11.next.controller.qna.ShowQuestionController;
import org.studyStepNext.part11.next.controller.qna.UpdateFormQuestionController;
import org.studyStepNext.part11.next.controller.qna.UpdateQuestionController;
import org.studyStepNext.part11.next.dao.AnswerDao;
import org.studyStepNext.part11.next.dao.JdbcAnswerDao;
import org.studyStepNext.part11.next.dao.JdbcQuestionDao;
import org.studyStepNext.part11.next.dao.QuestionDao;
import org.studyStepNext.part11.next.service.QnaService;

public class LegacyHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    public void initMapping() {
        QuestionDao questionDao = new JdbcQuestionDao();
        AnswerDao answerDao = new JdbcAnswerDao();
        QnaService qnaService = new QnaService(questionDao, answerDao);
        mappings.put("/", new HomeController(questionDao));
        mappings.put("/qna/show", new ShowQuestionController(questionDao, answerDao));
        mappings.put("/qna/form", new CreateFormQuestionController());
        mappings.put("/qna/create", new CreateQuestionController(questionDao));
        mappings.put("/qna/updateForm", new UpdateFormQuestionController(questionDao));
        mappings.put("/qna/update", new UpdateQuestionController(questionDao));
        mappings.put("/qna/delete", new DeleteQuestionController(qnaService));
        mappings.put("/api/qna/deleteQuestion", new ApiDeleteQuestionController(qnaService));
        mappings.put("/api/qna/list", new ApiListQuestionController(questionDao));
        mappings.put("/api/qna/addAnswer", new AddAnswerController(questionDao, answerDao));
        mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController(answerDao));

        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }

    @Override
    public Controller getHandler(HttpServletRequest request) {
        return mappings.get(request.getRequestURI());
    }
}

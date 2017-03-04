package org.studyStepNext.part11.core.di.factory.example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part11.core.annotation.Controller;
import org.studyStepNext.part11.core.annotation.Inject;
import org.studyStepNext.part11.core.annotation.RequestMapping;
import org.studyStepNext.part11.core.mvc.ModelAndView;
import org.studyStepNext.part11.core.nmvc.AbstractNewController;

@Controller
public class QnaController extends AbstractNewController {
    private MyQnaService qnaService;

    @Inject
    public QnaController(MyQnaService qnaService) {
        this.qnaService = qnaService;
    }

    public MyQnaService getQnaService() {
        return qnaService;
    }

    @RequestMapping("/questions")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jspView("/qna/list.jsp");
    }
}

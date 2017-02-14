package org.studyStepNext.part11.core.nmvc;

import org.studyStepNext.part11.core.mvc.JsonView;
import org.studyStepNext.part11.core.mvc.JspView;
import org.studyStepNext.part11.core.mvc.ModelAndView;

public abstract class AbstractNewController {
    protected ModelAndView jspView(String forwardUrl) {
        return new ModelAndView(new JspView(forwardUrl));
    }

    protected ModelAndView jsonView() {
        return new ModelAndView(new JsonView());
    }
}

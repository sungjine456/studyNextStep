package org.studyStepNext.part8.core.mvc;

public abstract class AbstractController implements Controller {
	protected ModelAndView jspView(String forwardUrl){
		return new ModelAndView(new JspView(forwardUrl));
	}
	protected ModelAndView jsonView() {
		return new ModelAndView(new JsonView());
	}
}
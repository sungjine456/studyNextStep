package org.studyStepNext.part10.core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part10.core.mvc.ModelAndView;

public class HandlerExecutionHandlerAdapter implements HandlerAdapter {

	@Override
	public boolean supports(Object handler) {
		return handler instanceof HandlerExecution;
	}

	@Override
	public ModelAndView handler(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		return ((HandlerExecution)handler).handle(req, resp);
	}
}

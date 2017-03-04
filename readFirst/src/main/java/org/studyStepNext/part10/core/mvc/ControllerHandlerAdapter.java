package org.studyStepNext.part10.core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part10.core.nmvc.HandlerAdapter;

public class ControllerHandlerAdapter implements HandlerAdapter {

	@Override
	public boolean supports(Object handler) {
		return handler instanceof Controller;
	}

	@Override
	public ModelAndView handler(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		return ((Controller)handler).execute(req, resp);
	}
}

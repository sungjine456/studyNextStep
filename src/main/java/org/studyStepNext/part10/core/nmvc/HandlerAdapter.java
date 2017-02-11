package org.studyStepNext.part10.core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.studyStepNext.part10.core.mvc.ModelAndView;

public interface HandlerAdapter {
	boolean supports(Object handler);
	ModelAndView handler(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception;
}

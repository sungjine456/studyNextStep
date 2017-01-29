package org.studyStepNext.part6.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
	String execute(HttpServletRequest req, HttpServletResponse res) throws Exception;
}

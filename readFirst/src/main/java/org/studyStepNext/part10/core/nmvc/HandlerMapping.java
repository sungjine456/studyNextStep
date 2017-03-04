package org.studyStepNext.part10.core.nmvc;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
	Object getHandler(HttpServletRequest req);
}

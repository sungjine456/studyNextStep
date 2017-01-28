package org.studyStepNext.part6.controller;

import org.studyStepNext.part6.webserver.HttpRequest;
import org.studyStepNext.part6.webserver.HttpResponse;

public interface Controller {
	void service(HttpRequest req, HttpResponse res);
}

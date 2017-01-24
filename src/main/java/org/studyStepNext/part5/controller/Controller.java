package org.studyStepNext.part5.controller;

import org.studyStepNext.part5.webserver.HttpRequest;
import org.studyStepNext.part5.webserver.HttpResponse;

public interface Controller {
	void service(HttpRequest req, HttpResponse res);
}

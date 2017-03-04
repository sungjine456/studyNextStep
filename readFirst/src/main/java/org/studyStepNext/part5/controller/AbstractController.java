package org.studyStepNext.part5.controller;

import org.studyStepNext.part5.webserver.HttpMethod;
import org.studyStepNext.part5.webserver.HttpRequest;
import org.studyStepNext.part5.webserver.HttpResponse;

public abstract class AbstractController implements Controller {

	@Override
	public void service(HttpRequest req, HttpResponse res) {
		HttpMethod method = HttpMethod.valueOf(req.getMethod());
		if(method.isPost()){
			doPost(req, res);
		} else {
			doGet(req, res);
		}
	}
	protected void doPost(HttpRequest req, HttpResponse res){
	}
	protected void doGet(HttpRequest req, HttpResponse res){
	}
}

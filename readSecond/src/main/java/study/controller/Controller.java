package study.controller;

import study.webserver.HttpRequest;
import study.webserver.HttpResponse;

public interface Controller {
	void service(HttpRequest req, HttpResponse res);
}

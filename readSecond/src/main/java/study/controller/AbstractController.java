package study.controller;

import study.webserver.HttpRequest;
import study.webserver.HttpResponse;

public abstract class AbstractController implements Controller {
	public void service(HttpRequest req, HttpResponse res){
		String method = req.getMethod();
		if("POST".equals(method)){
			doPost(req, res);
		} else {
			doGet(req,res);
		}
	}
	protected void doPost(HttpRequest req, HttpResponse res){
	}
	protected void doGet(HttpRequest req, HttpResponse res){
	}
}

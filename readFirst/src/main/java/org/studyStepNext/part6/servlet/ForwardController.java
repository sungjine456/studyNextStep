package org.studyStepNext.part6.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {
	private String forwardUrl;
	
	public ForwardController(String forwardUrl){
		this.forwardUrl = forwardUrl;
		if(forwardUrl == null){
			throw new NullPointerException("forwardUrl is null. 이동할 URL을 입력하세요.");
		}
	}
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res){
		return forwardUrl;
	}
}

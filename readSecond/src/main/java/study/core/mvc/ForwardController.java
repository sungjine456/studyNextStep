package study.core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {
	private String forwardUrl;
	
	public ForwardController(String forwardUrl){
		this.forwardUrl = forwardUrl;
		if(forwardUrl == null){
			throw new NullPointerException("forwardUrl is null");
		}
	}
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		return forwardUrl;
	}
}

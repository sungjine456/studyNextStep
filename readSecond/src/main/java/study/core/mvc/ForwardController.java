package study.core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController extends AbstractController {
	private String forwardUrl;
	
	public ForwardController(String forwardUrl){
		this.forwardUrl = forwardUrl;
		if(forwardUrl == null){
			throw new NullPointerException("forwardUrl is null");
		}
	}
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) {
		return jspView(forwardUrl);
	}
}

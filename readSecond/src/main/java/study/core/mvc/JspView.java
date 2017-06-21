package study.core.mvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JspView implements View {
	private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
	
	private String viewName;
	
	public JspView(String viewName){
		this.viewName = viewName;
	}

	@Override
	public void render(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
			res.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }
		RequestDispatcher rd = req.getRequestDispatcher(viewName);
		rd.forward(req, res);
	}
}

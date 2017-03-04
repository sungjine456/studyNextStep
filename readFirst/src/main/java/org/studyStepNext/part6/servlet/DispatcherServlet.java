package org.studyStepNext.part6.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part6.webserver.RequestMapping;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
	private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
	
	private RequestMapping rm;
	
	@Override
	public void init() throws ServletException {
		rm = new RequestMapping();
		rm.initMapping();
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String requestUri = req.getRequestURI();
		log.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);
		Controller controller = rm.getController(requestUri);
		try {
			String viewName = controller.execute(req, res);
			move(viewName, req, res);
		} catch(Throwable e) {
			log.error("Exception : {}", e);
			throw new ServletException(e.getMessage());
		}
	}
	
	private void move(String viewName, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		if(viewName.startsWith(DEFAULT_REDIRECT_PREFIX)){
			res.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
			return;
		}
		RequestDispatcher rd = req.getRequestDispatcher(viewName);
		rd.forward(req, res);
	}
}

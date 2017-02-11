package org.studyStepNext.part10.core.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part10.core.nmvc.AnnotationHandlerMapping;
import org.studyStepNext.part10.core.nmvc.HandlerExecution;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private LegacyHandlerMapping lhm;
    private AnnotationHandlerMapping ahm;

    @Override
    public void init() throws ServletException {
        lhm = new LegacyHandlerMapping();
        lhm.initMapping();
        ahm = new AnnotationHandlerMapping("org.studyStepNext.part10.next.controller");
        ahm.initialize();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Controller controller = lhm.findController(req.getRequestURI());
        try {
        	if(controller != null){
	            render(controller.execute(req, resp), req, resp);
        	} else {
        		HandlerExecution he = ahm.getHandler(req);
        		if(he == null){
        			throw new ServletException("유효하지 않은 요청입니다.");
        		}
        		render(he.handle(req, resp), req, resp);
        	}
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }
    
    public void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception{
    	View view = mav.getView();
    	view.render(mav.getModel(), req, resp);
    }
}

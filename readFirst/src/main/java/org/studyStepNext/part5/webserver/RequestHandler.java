package org.studyStepNext.part5.webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part5.controller.Controller;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
        	HttpRequest req = new HttpRequest(in);
        	HttpResponse res = new HttpResponse(out);
        	Controller controller = RequestMapping.getController(req.getPath());
        	if(controller == null){
        		String path = getDefultPath(req.getPath());
        		res.forward(path);
        	} else {
        		controller.service(req, res);
        	}
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private String getDefultPath(String path){
    	if("/".equals(path)){
    		return "/index.html";
    	}
    	return path;
    }
}

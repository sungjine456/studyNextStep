package study.webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.controller.Controller;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream();) {
        	HttpRequest req = new HttpRequest(in);
        	HttpResponse res = new HttpResponse(out);
        	
        	String url = req.getPath();
        	
        	Controller controller = RequestMapping.getControllerMap(url);
        	
        	if(controller != null){
        		controller.service(req, res);
        	} else {
        		res.forward(url);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

package org.studyStepNext.part3.webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part3.model.User;
import org.studyStepNext.part3.util.CustomUtils;
import org.studyStepNext.part3.util.HttpRequestUtils;
import org.studyStepNext.part3.util.IOUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream(); BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
        	String line = br.readLine();
        	String[] token = line.split(" ");
        	String url = token[1];
        	User user = null;
        	int contentLength = 0;
        	while(!"".equals(line)){
        		if(line == null){
        			break;
        		}
        		if(line.contains("Content-Length:")){
        			contentLength = Integer.parseInt(HttpRequestUtils.parseHeader(line).getValue());
        		}
        		line = br.readLine();
        	}
        	if(contentLength != 0){
        		user = CustomUtils.createUser(IOUtils.readData(br, contentLength));
        	}
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File("./WebContent" + url).toPath());
            if(url.equals("/user/create")){
            	response302Header(dos, body.length);
            } else {
            	response200Header(dos, body.length);
            }
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private void response302Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: /index.html");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

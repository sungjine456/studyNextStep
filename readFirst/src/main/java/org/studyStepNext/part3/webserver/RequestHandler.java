package org.studyStepNext.part3.webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part3.db.DataBase;
import org.studyStepNext.part3.model.User;
import org.studyStepNext.part3.util.CustomUtils;
import org.studyStepNext.part3.util.HttpRequestUtils;
import org.studyStepNext.part3.util.IOUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static boolean isLogined = false;

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
        	int contentLength = 0;
        	String contentType = "text/html";
        	Map<String, String> cookies = new HashMap<String, String>();
        	while(!"".equals(line)){
        		if(line == null){
        			break;
        		}
        		if(line.contains("Content-Length:")){
        			contentLength = Integer.parseInt(HttpRequestUtils.parseHeader(line).getValue());
        		}
        		if(line.contains("Cookie: ")){
        			cookies = HttpRequestUtils.parseCookies(HttpRequestUtils.parseHeader(line).getValue());
        		}
        		line = br.readLine();
        	}
            DataOutputStream dos = new DataOutputStream(out);
            if(url.equals("/user/create")){
            	DataBase.addUser(CustomUtils.createUser(IOUtils.readData(br, contentLength)));
            	response302Header(dos);
            } else if(url.equals("/user/login")) {
            	isLogined = false;
        		url = "/user/login_failed.html";
            	if(CustomUtils.isLogin(IOUtils.readData(br, contentLength))){
            		isLogined = true;
            		url = "/index.html";
            	}
            } else if(url.equals("/user/list")){
            	isLogined = false;
        		url = "/index.html";
            	if(isCookieLogined(cookies)){
            		StringBuilder sb = new StringBuilder();
            		Collection<User> findUsers = DataBase.findAll();
            		Iterator<User> usersIterator = findUsers.iterator();
            		while(usersIterator.hasNext()){
            			sb.append(usersIterator.next().toString());
            			sb.append("\n");
            		}
            		isLogined = true;
            		url = "/user/list.html";
            	}
            } else if(url.contains(".css")){
            	contentType = "text/css";
            }
            response200Header(dos, CustomUtils.makeReponseBody(url), contentType);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private boolean isCookieLogined(Map<String, String> cookies){
    	return !cookies.isEmpty() && cookies.containsKey("logined") && Boolean.parseBoolean(cookies.get("logined"));
    }
    
    private void response302Header(DataOutputStream dos) {
    	String url = "/index.html";
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + url);
            dos.writeBytes("\r\n");
            
            responseBody(dos, CustomUtils.makeReponseBody(url));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, byte[] body, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: "+ contentType +";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
           	dos.writeBytes("Set-Cookie: logined=" + isLogined + "\r\n");
            dos.writeBytes("\r\n");
            
            responseBody(dos, body);
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

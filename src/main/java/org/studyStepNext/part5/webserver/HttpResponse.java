package org.studyStepNext.part5.webserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
	private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
	DataOutputStream dos;
	private String redirectHeader = "HTTP/1.1 302 Redirect \r\n";
	private byte[] body;
	
	public HttpResponse(OutputStream out){
		dos = new DataOutputStream(out);
	}
	
	public void addHeader(String header, String value){
		redirectHeader += header + ": " + value + " \r\n";
	}
	public void forward(String url){
		try {
			forwardBody(url);
			response200Header(body.length);
			responseBody(body);
		} catch(IOException e){
			log.debug(e.getMessage());
		}
	}
	private void forwardBody(String url) throws IOException { 
		body = Files.readAllBytes(new File("./WebContent" + url).toPath());
	}
	private void response200Header(int lengthOfBodyContent){
		try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
	}
	private void responseBody(byte[] body){
		try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
	}
	public void sendRedirect(String url){
		try {
            dos.writeBytes(redirectHeader);
            dos.writeBytes("Location: " + url + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
	}
	private void processHeaders(){
		
	}
}

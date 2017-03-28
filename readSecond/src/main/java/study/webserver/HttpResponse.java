package study.webserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
	private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

	private DataOutputStream dos;
	private StringBuilder sb = new StringBuilder();

	public HttpResponse(OutputStream ops) {
		dos = new DataOutputStream(ops);
	}

	public void addHeader(String key, String value) {
		sb.append(key+": "+value+" \r\n");
	}

	public void forward(String url) {
		try {
			byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
			
			response200Header(body.length);
			forwardBody(url);
			
			responseBody(body);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void sendRedirect(String url) {
		try {
			byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
			
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            processHeaders();
            dos.writeBytes("Location: " + url + " \r\n");
            dos.writeBytes("\r\n");
            
            responseBody(body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
	}
	
	private void forwardBody(String url) {
		try {
			if (url.endsWith(".css")) {
				dos.writeBytes("Content-Type: text/css \r\n");
			} else if (url.endsWith(".js")) {
				dos.writeBytes("Content-Type: application/javascript \r\n");
			} else {
				dos.writeBytes("Content-Type: text/html;charset=utf-8 \r\n");
			}
		} catch (IOException e) {
            log.error(e.getMessage());
        }
	}

	private void response200Header(int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void responseBody(byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void processHeaders() {
		try {
			dos.writeBytes(sb.toString());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}

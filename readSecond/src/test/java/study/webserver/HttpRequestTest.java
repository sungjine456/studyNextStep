package study.webserver;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

public class HttpRequestTest {
	private String testDirectory = "./src/test/resources/";
	
	@Test
	public void request_GET() throws Exception {
		InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
		HttpRequest req = new HttpRequest(in);
		
		assertEquals("GET", req.getMethod());
		assertEquals("/user/create", req.getPath());
		assertEquals("keep-alive", req.getHeader("Connection"));
		assertEquals("testUserId", req.getParameter("userId"));
	}
	
	@Test
	public void request_POST() throws Exception {
		InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
		HttpRequest req = new HttpRequest(in);
		
		assertEquals("POST", req.getMethod());
		assertEquals("/user/create", req.getPath());
		assertEquals("keep-alive", req.getHeader("Connection"));
		assertEquals("testUserId", req.getParameter("userId"));
	}
}

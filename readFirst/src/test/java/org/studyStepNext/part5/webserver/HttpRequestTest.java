package org.studyStepNext.part5.webserver;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

public class HttpRequestTest {
	private String testDirectory = "./src/test/resources/";

	@Test
	public void request_GET() throws Exception {
		InputStream in = new FileInputStream(new File(testDirectory + "part5/Http_GET.txt"));
		HttpRequest request = new HttpRequest(in);
		
		assertEquals("GET", request.getMethod());
		assertEquals("/user/create", request.getPath());
		assertEquals("keep-alive", request.getHeader("Connection"));
		assertEquals("id", request.getParameter("userId"));
		assertEquals("pas", request.getParameter("password"));
		assertEquals("name", request.getParameter("name"));
	}

	@Test
	public void request_POST() throws Exception {
		InputStream in = new FileInputStream(new File(testDirectory + "part5/Http_POST.txt"));
		HttpRequest request = new HttpRequest(in);
		
		assertEquals("POST", request.getMethod());
		assertEquals("/user/create", request.getPath());
		assertEquals("keep-alive", request.getHeader("Connection"));
		assertEquals("id", request.getParameter("userId"));
		assertEquals("pas", request.getParameter("password"));
		assertEquals("name", request.getParameter("name"));
	}
}

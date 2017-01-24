package org.studyStepNext.part5.webserver;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class RequestLineTest {

	@Test
	public void create_method() {
		RequestLine line = new RequestLine("GET /index.html HTTP/1.1");
		assertEquals("GET", line.getMethod().name());
		assertEquals("/index.html", line.getPath());
		
		line = new RequestLine("POST /index.html HTTP/1.1");
		assertEquals("POST", line.getMethod().name());
		assertEquals("/index.html", line.getPath());
	}

	@Test
	public void create_path_and_params() {
		RequestLine line = new RequestLine("GET /user/create?userId=id&password=pass HTTP/1.1");
		assertEquals("GET", line.getMethod().name());
		assertEquals("/user/create", line.getPath());
		Map<String, String> map = line.getParams();
		assertEquals(2, map.size());
		assertEquals("id", map.get("userId"));
		assertEquals("pass", map.get("password"));
	}
	
	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void illegalArgumentExceptionTest(){
		RequestLine line = new RequestLine("GET HTTP/1.1");
	}
}

package org.studyStepNext.part6.webserver;

import java.util.Map;

import org.studyStepNext.part6.util.HttpRequestUtils;

public class HttpCookie {
	private Map<String, String> cookies;
	HttpCookie(String cookieValue){
		cookies = HttpRequestUtils.parseCookies(cookieValue);
	}
	public String getCookie(String name){
		return cookies.get(name);
	}
}

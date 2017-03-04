package org.studyStepNext.part6.webserver;

public enum HttpMethod {
	GET,
	POST;
	
	public boolean isPost(){
		return this == POST;
	}
}

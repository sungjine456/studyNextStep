package org.studyStepNext.part5.webserver;

public enum HttpMethod {
	GET,
	POST;
	
	public boolean isPost(){
		return this == POST;
	}
}

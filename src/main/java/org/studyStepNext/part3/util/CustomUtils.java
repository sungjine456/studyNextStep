package org.studyStepNext.part3.util;

import java.util.Map;

import org.studyStepNext.part3.model.User;

public class CustomUtils {
	public static User createUser(String parseString){
		User user = null;
		Map<String, String> map = HttpRequestUtils.parseQueryString(parseString);
		user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
		
    	return user;
	}
}

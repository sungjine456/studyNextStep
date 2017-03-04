package org.studyStepNext.part3.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import org.studyStepNext.part3.db.DataBase;
import org.studyStepNext.part3.model.User;

public class CustomUtils {
	public static User createUser(String parseString){
		Map<String, String> map = HttpRequestUtils.parseQueryString(parseString);
    	return new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
	}
	
	 public static boolean isLogin(String parseString){
    	Map<String, String> map = HttpRequestUtils.parseQueryString(parseString);
    	String userId = map.get("userId");
    	String password = map.get("password");
    	User user = DataBase.findUserById(userId);
    	if(userId == null || password == null || user == null){
    		return false;
    	}
    	if(userId.equals(user.getUserId()) && password.equals(user.getPassword())){
    		return true;
    	}
    	return false;
    }
	 
	 public static byte[] makeReponseBody(String url) throws IOException {
		 return Files.readAllBytes(new File("./WebContent" + url).toPath());
	 }
}

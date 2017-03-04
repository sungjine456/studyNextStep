package org.studyStepNext.part3.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

import org.junit.Test;
import org.studyStepNext.part3.db.DataBase;
import org.studyStepNext.part3.model.User;

public class CustomUtilsTest {

	@Test
	public void createUser(){
		String parseString = "userId=1&password=2&name=a&email=asdf@naver.com";
		User user = CustomUtils.createUser(parseString);
		assertThat(user.getUserId(), is("1"));
		assertThat(user.getPassword(), is("2"));
		assertThat(user.getName(), is("a"));
		assertThat(user.getEmail(), is("asdf@naver.com"));
	}
	
	@Test
	public void isLogin(){
		DataBase.addUser(new User("1", "1", "1", "1@naver.com"));
		String parseString = "userId=1&password=1";
		assertThat(CustomUtils.isLogin(parseString), is(true));
		parseString = "userId=1&password=2";
		assertThat(CustomUtils.isLogin(parseString), is(false));
		parseString = "userId=2&password=1";
		assertThat(CustomUtils.isLogin(parseString), is(false));
		parseString = "userId=2&password=2";
		assertThat(CustomUtils.isLogin(parseString), is(false));
	}
	
	@Test
	public void makeReponseBody() throws IOException {
		assertThat(CustomUtils.makeReponseBody("/index.html"), notNullValue());
	}
	
	@Test(expected=NoSuchFileException.class)
	public void makeReponseBodyNoFile() throws IOException {
		assertThat(CustomUtils.makeReponseBody("/user/create").length, is(0));
	}
}

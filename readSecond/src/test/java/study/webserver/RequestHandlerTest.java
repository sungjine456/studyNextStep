package study.webserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

import study.model.User;

public class RequestHandlerTest {
	private Class<RequestHandler> clazz; 
	private RequestHandler requestHandler;
	
	@Before
	public void setup() throws Exception {
		clazz = RequestHandler.class;
		Constructor<?> con = clazz.getConstructor(new Class[]{Socket.class});
		requestHandler = (RequestHandler)con.newInstance(new Socket());
	}
	
	@Test
	public void test() throws Exception {
		String line = "GET /index.html Http/1.1";
		Method method = clazz.getDeclaredMethod("getUrl", String.class);
		method.setAccessible(true);
		String result = (String)method.invoke(requestHandler, line);
		assertEquals(result, "/index.html");
	}
	
	@Test
	public void getUserTest() throws Exception {
		Method method = clazz.getDeclaredMethod("getUser", String.class);
		method.setAccessible(true);
		
		String line = "userId=javajigi&password=password2";
		User result = (User)method.invoke(requestHandler, line);
		assertEquals(result.getUserId(), "javajigi");
		assertEquals(result.getPassword(), "password2");
		assertNull(result.getEmail());
		assertNull(result.getName());
		
		line = "userId=javajigi&password=password2&name=c&email=a%40com";
		result = (User)method.invoke(requestHandler, line);
		assertEquals(result.getUserId(), "javajigi");
		assertEquals(result.getPassword(), "password2");
		assertEquals(result.getEmail(), "a%40com");
		assertEquals(result.getName(), "c");
	}
}

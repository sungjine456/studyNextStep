package study.webserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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
	public void getUserTest() throws Exception {
		Class<HttpRequest> httpRequestClazz = HttpRequest.class;
		Constructor<HttpRequest> httpRequestCon = httpRequestClazz.getConstructor(new Class[]{});
		HttpRequest httpRequest = httpRequestCon.newInstance();
		
		Field httpRequestParametersField = httpRequestClazz.getDeclaredField("parameters");
		httpRequestParametersField.setAccessible(true);
		
		Map<String, String> map = new HashMap<>();
		map.put("userId", "testId");
		map.put("password", "password");
		
		httpRequestParametersField.set(httpRequest, map);
		
		Method method = clazz.getDeclaredMethod("getUser", HttpRequest.class);
		method.setAccessible(true);
		
		User result = (User)method.invoke(requestHandler, httpRequest);
		assertEquals(result.getUserId(), "testId");
		assertEquals(result.getPassword(), "password");
		assertNull(result.getEmail());
		assertNull(result.getName());
		
		map.put("name", "testName");
		map.put("email", "test@email.com");
		
		httpRequestParametersField.set(httpRequest, map);
		
		result = (User)method.invoke(requestHandler, httpRequest);
		assertEquals(result.getUserId(), "testId");
		assertEquals(result.getPassword(), "password");
		assertEquals(result.getEmail(), "test@email.com");
		assertEquals(result.getName(), "testName");
	}
}

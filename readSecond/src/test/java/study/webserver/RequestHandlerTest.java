package study.webserver;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

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
}

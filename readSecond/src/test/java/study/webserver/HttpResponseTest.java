package study.webserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.Test;

public class HttpResponseTest {
	private String testDirectory = "./src/test/resources/";

	@Test
	public void responseForward() throws Exception {
		HttpResponse res = new HttpResponse(createOutputStream("Http_Forward.txt"));
		res.forward("/index.html");
	}

	@Test
	public void responseRedirect() throws Exception {
		HttpResponse res = new HttpResponse(createOutputStream("Http_Redirect.txt"));
		res.sendRedirect("/index.html");
	}
	
	@Test
	public void responseCookies() throws Exception {
		HttpResponse res = new HttpResponse(createOutputStream("Http_Cookie.txt"));
		res.addHeader("Set-Cookie", "logined=true");
		res.sendRedirect("/index.html");
	}
	
	private OutputStream createOutputStream(String filename)throws FileNotFoundException{
		return new FileOutputStream(new File(testDirectory + filename));
	}
}

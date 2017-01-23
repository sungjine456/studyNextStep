package org.studyStepNext.part5.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part5.util.HttpRequestUtils;

public class HttpRequest {
	private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

	private String method;
	private String path;
	private Map<String, String> headers = new HashMap<String, String>();
	private Map<String, String> parameters = new HashMap<String, String>();
	
	public HttpRequest(InputStream in) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(in))){
			String line = br.readLine();
			String[] splitLine = line.split(" ");
			method = splitLine[0];
			if(method.equals("POST")){
				path = splitLine[1];
				line = br.readLine();
				while(!line.equals("")){
					log.debug(line);
					String[] splitHeader = line.split(": ");
					headers.put(splitHeader[0], splitHeader[1]);
					line = br.readLine();
				}
				parameters = HttpRequestUtils.parseQueryString(br.readLine());
			} else {
				System.out.println(splitLine[1]);
				String[] splitUrl = splitLine[1].split("\\?");
				path = splitUrl[0];
				parameters = HttpRequestUtils.parseQueryString(splitUrl[1]);
				line = br.readLine();
				while(line != null && !line.equals("")){
					log.debug(line);
					String[] splitHeader = line.split(": ");
					headers.put(splitHeader[0], splitHeader[1]);
					line = br.readLine();
				}
			}
		}catch(IOException e){
			log.error(e.getMessage());
		}
	}
	
	public String getMethod(){
		return method;
	}
	public String getPath(){
		return path;
	}
	public String getHeader(String header){
		return headers.get(header);
	}
	public String getParameter(String parameter){
		return parameters.get(parameter);
	}
}

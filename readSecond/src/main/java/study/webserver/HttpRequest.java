package study.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import study.util.HttpRequestUtils;
import study.util.HttpRequestUtils.Pair;
import study.util.IOUtils;

public class HttpRequest {
	private BufferedReader br;
	private String method;
	private String path;
	private Map<String, String> headers = new HashMap<>();
	private Map<String, String> parameters = new HashMap<>();
	
	public HttpRequest(){}
	
	public HttpRequest(InputStream in) {
		br = new BufferedReader(new InputStreamReader(in));
		try {
			String line = br.readLine();
			String[] lineSplit = line.split(" ");
			method = lineSplit[0];
			String[] pathSplit = lineSplit[1].split("\\?");
			path = pathSplit[0];
			if(pathSplit.length>1){
				parameters = HttpRequestUtils.parseQueryString(pathSplit[1]);
			}
			line = br.readLine();
			while(line != null && !"".equals(line)){
				Pair pair = HttpRequestUtils.parseHeader(line);
				headers.put(pair.getKey(), pair.getValue());
				line = br.readLine();
			}
			if(method.equals("POST")){
				parameters = HttpRequestUtils.parseQueryString(IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length"))));
			}
		} catch(IOException e){
			e.printStackTrace();
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

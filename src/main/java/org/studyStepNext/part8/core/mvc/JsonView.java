package org.studyStepNext.part8.core.mvc;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonView implements View {
	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		resp.setContentType("application/json/charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(mapper.writeValueAsString(createModel(req)));
	}
	
	private Map<String, String> createModel(HttpServletRequest req){
		Enumeration<String> names = req.getAttributeNames();
		Map<String, String> model = new HashMap<String, String>();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			model.put(name, (String)req.getAttribute(name));
		}
		return model;
	}
}

package study.core.mvc;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonView implements View {
	@Override
    public void render(HttpServletRequest req, HttpServletResponse res) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.print(mapper.writeValueAsString(createModel(req)));
    }
	
	private Map<String, Object> createModel(HttpServletRequest req){
		Enumeration<String> names = req.getAttributeNames();
		Map<String, Object> model = new HashMap<>();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			model.put(name, req.getAttribute(name));
		}
		return model;
	}
}

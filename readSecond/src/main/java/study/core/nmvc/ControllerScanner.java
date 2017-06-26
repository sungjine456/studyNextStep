package study.core.nmvc;

import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import study.core.annotation.Controller;

public class ControllerScanner {
	private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);
	
	public Map<Class<?>, Object> controllers;
	
	public ControllerScanner(){
		Reflections reflections = new Reflections("study.next");
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
		
		controllers = Maps.newHashMap();
		
		try {
            for (Class<?> clazz : annotated) {
                controllers.put(clazz, clazz.newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage());
        }
	}
	
	public Map<Class<?>, Object> getControllers(){
		return controllers;
	}
}

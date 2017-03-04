package org.studyStepNext.part10.core.nmvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part10.core.annotation.Controller;

public class ControllerScanner {
	private static Logger log = LoggerFactory.getLogger(ControllerScanner.class);
	private Reflections reflections;
	
	public ControllerScanner(Object... basePackage){
		reflections = new Reflections(basePackage);
	}
	
	public Map<Class<?>, Object> getControllers(){
		Set<Class<?>> preInitiatedControllers = reflections.getTypesAnnotatedWith(Controller.class);
		return instantiateControllers(preInitiatedControllers);
	}
	
	Map<Class<?>, Object> instantiateControllers(Set<Class<?>> preInitiatedControllers){
		Map<Class<?>, Object> controllers = new HashMap<Class<?>, Object>();
		try {
			for(Class<?> controller : preInitiatedControllers){
				controllers.put(controller, controller.newInstance());
			}
		} catch(Exception e){
			log.error(e.getMessage());
		}
		return controllers;
	}
}

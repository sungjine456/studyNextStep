package org.studyStepNext.part10.core.nmvc;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.reflections.ReflectionUtils;
import org.studyStepNext.part10.core.annotation.RequestMapping;
import org.studyStepNext.part10.core.annotation.RequestMethod;

import com.google.common.collect.Maps;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @SuppressWarnings("unchecked")
	public void initialize() {
    	ControllerScanner controllerScanner = new ControllerScanner(basePackage);
    	Map<Class<?>, Object> controllers = controllerScanner.getControllers();
    	Set<Class<?>> clazzes = controllers.keySet();
    	Set<Method> methods = new HashSet<Method>();
    	for(Class<?> clazz : clazzes){
    		methods.addAll(ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class)));
    	}
    	for(Method method : methods){
    		RequestMapping rm = method.getAnnotation(RequestMapping.class);
    		handlerExecutions.put(createHandlerKey(rm), new HandlerExecution(controllers.get(method.getDeclaringClass()), method));
    	}
    }
    
    private HandlerKey createHandlerKey(RequestMapping rm){
    	return new HandlerKey(rm.value(), rm.method());
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }
}

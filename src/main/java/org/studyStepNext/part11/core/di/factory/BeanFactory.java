package org.studyStepNext.part11.core.di.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.studyStepNext.part11.core.annotation.Controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BeanFactory {
    private Set<Class<?>> preInstanticateBeans;
    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
    	for(Class<?> clazz : preInstanticateBeans) {
    		if(beans.get(clazz) == null){
    			instantiateClass(clazz);
    		}
    	}
    }
    
    private Object instantiateClass(Class<?> clazz) {
    	Object bean = beans.get(clazz);
    	Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
    	if(injectedConstructor == null) {
    		bean = BeanUtils.instantiate(clazz);
    		beans.put(clazz, bean);
    		return bean;
    	}
    	bean = instantiateConstructor(injectedConstructor);
    	beans.put(clazz, bean);
    	return bean;
    }
    
    private Object instantiateConstructor(Constructor<?> constructor) {
    	Class<?>[] pTypes = constructor.getParameterTypes();
    	List<Object> args =  Lists.newArrayList();
    	for(Class<?> clazz : pTypes) {
    		Class<?> concreateClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
    		if(!preInstanticateBeans.contains(concreateClazz)) {
    			throw new IllegalStateException(clazz + "는 Bean이 아니다.");
    		}
    		Object bean = beans.get(concreateClazz);
    		if(bean == null){
    			bean = instantiateClass(concreateClazz);
    		}
    		args.add(bean);
    	}
    	return BeanUtils.instantiateClass(constructor, args.toArray());
    }
    
    public Map<Class<?>, Object> getControllers() {
    	Map<Class<?>, Object> controllers = Maps.newHashMap();
    	for(Class<?> clazz : preInstanticateBeans) {
    		Annotation annotation = clazz.getAnnotation(Controller.class);
    		if(annotation != null){
    			controllers.put(clazz, beans.get(clazz));
    		}
    	}
    	return controllers;
    }
}

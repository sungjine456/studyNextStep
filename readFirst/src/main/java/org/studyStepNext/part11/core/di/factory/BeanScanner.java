package org.studyStepNext.part11.core.di.factory;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.reflections.Reflections;
import org.studyStepNext.part11.core.annotation.Controller;
import org.studyStepNext.part11.core.annotation.Repository;
import org.studyStepNext.part11.core.annotation.Service;

import com.google.common.collect.Sets;

public class BeanScanner {
	private Reflections reflections;
	
	public BeanScanner(Object... basePackage) {
		reflections = new Reflections(basePackage);
	}
	
	@SuppressWarnings("unchecked")
	public Set<Class<?>> scan() {
		return getTypesAnnotatedWith(Controller.class, Service.class, Repository.class);
	}
	
	@SuppressWarnings("unchecked")
	private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
		Set<Class<?>> preInstantiatedBeans = Sets.newHashSet();
		for(Class<? extends Annotation> annotation : annotations) {
			preInstantiatedBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
		}
		return preInstantiatedBeans;
	}
}

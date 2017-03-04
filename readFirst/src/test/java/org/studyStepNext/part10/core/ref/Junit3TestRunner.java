package org.studyStepNext.part10.core.ref;

import java.lang.reflect.Method;

import org.junit.Test;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Method[] methods = clazz.getMethods();
        for(int i = 0; i < methods.length; i++){
        	String methodName = methods[i].getName();
        	if(methodName.startsWith("test")){
        		methods[i].invoke(clazz.newInstance());
        	}
        }
    }
}

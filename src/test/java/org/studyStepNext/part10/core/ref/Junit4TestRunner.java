package org.studyStepNext.part10.core.ref;

import java.lang.reflect.Method;

import org.junit.Test;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Method[] methods = clazz.getMethods();
        for(int i = 0; i < methods.length; i++){
        	if(methods[i].isAnnotationPresent(MyTest.class)){
        		methods[i].invoke(clazz.newInstance());
        	}
        }
    }
}

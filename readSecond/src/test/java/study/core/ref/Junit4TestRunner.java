package study.core.ref;

import java.lang.reflect.Method;

import org.junit.Test;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        for(Method method : clazz.getMethods()){
        	if(method.isAnnotationPresent(MyTest.class)){
        		method.invoke(clazz.newInstance());
        	}
        }
    }
}

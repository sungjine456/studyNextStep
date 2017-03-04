package org.studyStepNext.part10.core.ref;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studyStepNext.part10.next.model.Question;
import org.studyStepNext.part10.next.model.User;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());
        Constructor<?>[] clazzConstructors = clazz.getConstructors();
        Method[] methods = clazz.getMethods();
        Field[] fileds = clazz.getDeclaredFields();
        int conLen = clazzConstructors.length;
        int meLen = methods.length;
        int filedLen = fileds.length;
        for(int i = 0; i < conLen; i++){
        	logger.debug(clazzConstructors[i].getName()+ ", parametersCount : "+ clazzConstructors[i].getParameterCount());
        }
        for(int i = 0; i < meLen; i++){
        	logger.debug(methods[i].getReturnType() + " " + methods[i].getName());
        }
        for(int i = 0; i < filedLen; i++){
        	logger.debug(fileds[i].getName());
        }
    }
    
    @Test
    public void newInstanceWithConstructorArgs() throws Exception {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
        Constructor<?>[] userCons = clazz.getDeclaredConstructors();
        Constructor<?> userCon = userCons[0];

        User user = (User)userCon.newInstance("id", "password", "name", "email");
		assertEquals("id", user.getUserId());
		assertEquals("password", user.getPassword());
		assertEquals("name", user.getName());
		assertEquals("email", user.getEmail());
    }
    
    @Test
    public void privateFieldAccess() throws Exception {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());
        Field nameField = clazz.getDeclaredField("name");
        Field ageField = clazz.getDeclaredField("age");
        nameField.setAccessible(true);
        ageField.setAccessible(true);
        Student student = clazz.newInstance();
        nameField.set(student, "주한");
        ageField.set(student, 14);
        assertEquals("주한", student.getName());
        assertEquals(14, student.getAge());
    }
}

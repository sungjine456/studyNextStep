package study.core.ref;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.next.model.Question;
import study.next.model.User;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());
        for(Field field : clazz.getDeclaredFields()){
        	logger.info(field.getName());
        }
        for(Constructor<?> con : clazz.getDeclaredConstructors()){
        	logger.info(con.getName() + " " + con.getParameterCount());
        }
        for(Method method : clazz.getDeclaredMethods()){
        	logger.info(method.getName());
        }
    }
    
    @Test
    public void newInstanceWithConstructorArgs() throws Exception {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
        Constructor<User> con = clazz.getConstructor(String.class,String.class,String.class,String.class);
        User user = con.newInstance("id","password","name","email");
        assertThat(user.getName(), is("name"));
        assertThat(user.getEmail(), is("email"));
        assertThat(user.getPassword(), is("password"));
        assertThat(user.getUserId(), is("id"));
    }
    
    @Test
    public void privateFieldAccess() throws Exception {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());
        Student student = clazz.newInstance();
        Field age = clazz.getDeclaredField("age");
        Field name = clazz.getDeclaredField("name");
        age.setAccessible(true);
        name.setAccessible(true);
        age.setInt(student, 10);
        name.set(student, "jon");
        assertThat(student.getAge(), is(10));
        assertThat(student.getName(), is("jon"));
    }
}

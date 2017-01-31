package org.studyStepNext.part7.next.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import org.studyStepNext.part7.core.jdbc.ConnectionManager;
import org.studyStepNext.part7.next.model.User;

public class UserDaoTest {
    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void crud() throws Exception {
        User expected = new User("userId", "password", "name", "javajigi@email.com");
        UserDao userDao = new UserDao();
        userDao.insert(expected);
        User actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);

        expected.update(new User("userId", "password2", "name2", "sanjigi@email.com"));
        userDao.update(expected);
        actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);
        assertEquals("password2", actual.getPassword());
        assertEquals("name2", actual.getName());
        assertEquals("sanjigi@email.com", actual.getEmail());
    }

    @Test
    public void findAll() throws Exception {
        UserDao userDao = new UserDao();
        List<User> users = userDao.findAll();
        assertEquals(1, users.size());
    }
}
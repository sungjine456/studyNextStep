package org.studyStepNext.part9.next.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import org.studyStepNext.part9.core.jdbc.ConnectionManager;
import org.studyStepNext.part9.next.model.Question;

public class QuestionDaoTest {
    private static final Logger log = LoggerFactory.getLogger(QuestionDaoTest.class);
    
    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }
    
    @Test
    public void crud() {
        Question question = new Question("writer", "title", "contents");
        QuestionDao questionDao = new QuestionDao();
        Question savedQuestion = questionDao.insert(question);
        log.debug("question : {}", savedQuestion);
    }

    @Test
    public void addCount() {
    	QuestionDao questionDao = new QuestionDao();
    	assertEquals(0, questionDao.findById(1).getCountOfComment());
    	questionDao.addCount(1);
    	assertEquals(1, questionDao.findById(1).getCountOfComment());
    }
}

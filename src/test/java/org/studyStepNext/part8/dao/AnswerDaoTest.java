package org.studyStepNext.part8.dao;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.studyStepNext.part8.core.jdbc.ConnectionManager;
import org.studyStepNext.part8.next.dao.AnswerDao;
import org.studyStepNext.part8.next.model.Answer;

public class AnswerDaoTest {
	
    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void addAnswer() throws Exception {
        int questionId = 1;
        Answer expected = new Answer(0, "javajigi", "answer contents", new Date(), questionId);
        AnswerDao ad = new AnswerDao();
        Answer answer = ad.insert(expected);
        assertEquals(answer.getWriter(), "javajigi");
    }
    
    @Test
    public void findById() throws Exception {
    	AnswerDao ad = new AnswerDao();
    	Answer answer = ad.findById(1);
    	assertEquals(answer.getWriter(), "eungju");
    }
    
    @Test
    public void findAll() throws Exception {
    	AnswerDao ad = new AnswerDao();
        List<Answer> answers = ad.findAllByQuestionId(7);
        assertEquals(2, answers.size());
    }
}

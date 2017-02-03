package org.studyStepNext.part8.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.studyStepNext.part8.core.jdbc.ConnectionManager;
import org.studyStepNext.part8.next.dao.QuestionDao;
import org.studyStepNext.part8.next.model.Question;

public class QuestionDaoTest {

	@Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void addAnswer() throws Exception {
        Question expected = new Question(0, "javajigi", "title", "answer contents", new Date(), 0);
        QuestionDao qd = new QuestionDao();
        Question question = qd.insert(expected);
        assertEquals(question.getWriter(), "javajigi");
    }
    
    @Test
    public void findById() throws Exception {
    	QuestionDao questionDao = new QuestionDao();
    	Question question = questionDao.findById(2);
    	assertEquals(question.getWriter(), "김문수");
    }
    
    @Test
    public void findAll() throws Exception {
    	QuestionDao qd = new QuestionDao();
        List<Question> questions = qd.findAll();
        assertEquals(8, questions.size());
    }
}

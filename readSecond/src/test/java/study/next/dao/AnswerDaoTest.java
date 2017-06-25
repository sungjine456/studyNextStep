package study.next.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import study.core.jdbc.ConnectionManager;
import study.next.model.Answer;

public class AnswerDaoTest {
	private AnswerDao answerDao = AnswerDao.getInstance();
	
	@Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void findAll() throws Exception {
        List<Answer> answers = answerDao.findAllByQuestionId(8L);
        assertEquals(3, answers.size());
        answers = answerDao.findAllByQuestionId(7L);
        assertEquals(2, answers.size());
    }
    
    @Test
    public void addAnswer() throws Exception {
        long questionId = 1L;
        Answer expected = new Answer("javajigi", "answer contents", questionId);
        Answer answer = answerDao.insert(expected);
        assertThat(answerDao.findAllByQuestionId(questionId).size(), is(1));
        assertThat(answer, is(expected));
    }
}

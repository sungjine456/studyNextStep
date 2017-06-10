package study.next.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import study.core.jdbc.ConnectionManager;
import study.next.model.Question;

public class QuestionDaoTest {
	@Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }
	
	@Test
	public void findAllTest(){
    	QuestionDao questionDao = new QuestionDao();
		List<Question> questions = questionDao.findAll();
		assertThat(questions.size(), is(8));
	}

    @Test
    public void findByQuestionIdTest() throws Exception {
    	QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findByQuestionId(1L);
        assertThat(question.getWriter(), is("자바지기"));
        assertTrue(question.getTitle().startsWith("국내에서"));
        assertTrue(question.getContents().startsWith("Ruby on Rails"));
        assertThat(question.getCountOfAnswer(), is(0));
    }
}

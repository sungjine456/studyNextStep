package org.studyStepNext.part10.next.model;

import static org.junit.Assert.assertTrue;
import static org.studyStepNext.part10.next.model.AnswerTest.newAnswer;
import static org.studyStepNext.part10.next.model.UserTest.newUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.studyStepNext.part10.next.CannotDeleteException;

public class QuestionTest {
	public static Question newQuestion(String writer){
		return new Question(1L, writer, "title", "contents", new Date(), 0);
	}
	public static Question newQuestion(long questionId, String writer) {
        return new Question(questionId, writer, "title", "contents", new Date(), 0);
    }
	private User user;
	
	@Before
	public void setup(){
		user = newUser("testUser");
	}
	
	@Test(expected=CannotDeleteException.class)
	public void canDelete_deferentWriter() throws Exception {
		Question question = newQuestion("anotherTestUser");
		question.canDelete(user, new ArrayList<Answer>());
	}
	
	@Test
	public void canDelete_sameWriter_noneAnswer() throws Exception {
		Question question = newQuestion("testUser");
		assertTrue(question.canDelete(user, new ArrayList<Answer>()));
	}
	
	@Test
	public void canDelete_sameUserAsnwerExist() throws Exception {
		Question question = newQuestion("testUser");
		List<Answer> answers = Arrays.asList(newAnswer("testUser"));
		assertTrue(question.canDelete(user, answers));
	}
	
	@Test(expected=CannotDeleteException.class)
	public void canDelte_anotherWriterAnswerExist() throws Exception {
		List<Answer> answers = Arrays.asList(newAnswer("testUser"), newAnswer("anotherTestUser"));
		newQuestion("testUser").canDelete(user, answers);
	}
}

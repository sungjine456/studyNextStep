package org.studyStepNext.part10.next.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.studyStepNext.part10.next.model.UserTest.newUser;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.studyStepNext.part10.next.CannotDeleteException;
import org.studyStepNext.part10.next.dao.AnswerDao;
import org.studyStepNext.part10.next.dao.QuestionDao;
import org.studyStepNext.part10.next.model.Answer;
import org.studyStepNext.part10.next.model.Question;
import org.studyStepNext.part10.next.model.User;

@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTest {
	@Mock
	private QuestionDao questionDao;
	@Mock
	private AnswerDao answerDao;
	private QnaService qnaService;
	private User user;
	
	@Before
	public void setup(){
		user = newUser("testUser");
		qnaService = new QnaService(questionDao, answerDao);
	}
	
	@Test(expected=CannotDeleteException.class)
	public void deletQuestion_noneQuestion()throws Exception{
		when(questionDao.findById(1L)).thenReturn(null);
		
		qnaService.deleteQuestion(1L, user);
	}
	
	@Test
	public void canDeleteQuestion() throws Exception {
		Question question = new Question(1L, user.getUserId(), "title", "contents", new Date(), 0) {
			@Override
			public boolean canDelete(User user, List<Answer> answers) throws CannotDeleteException {
				return true;
			}
		};
		when(questionDao.findById(1L)).thenReturn(question);
		
		qnaService.deleteQuestion(1L, newUser("userId"));
		verify(questionDao).delete(question.getQuestionId());
	}
	
	@Test(expected=CannotDeleteException.class)
	public void cannotDeleteQuestion() throws Exception {
		Question question = new Question(1L, user.getUserId(), "title", "contents", new Date(), 0) {
			@Override
			public boolean canDelete(User user, List<Answer> answers) throws CannotDeleteException {
				throw new CannotDeleteException("삭제할 수 없음");
			}
		};
		when(questionDao.findById(1L)).thenReturn(question);
		
		qnaService.deleteQuestion(1L, newUser("userId"));
	}
}

package org.studyStepNext.part10.next.service;

import static org.studyStepNext.part10.next.model.UserTest.newUser;
import static org.studyStepNext.part10.next.model.QuestionTest.newQuestion;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.studyStepNext.part10.next.CannotDeleteException;
import org.studyStepNext.part10.next.dao.AnswerDao;
import org.studyStepNext.part10.next.dao.QuestionDao;
import org.studyStepNext.part10.next.model.Question;

import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTest {
	@Mock
	private QuestionDao questionDao;
	@Mock
	private AnswerDao answerDao;
	
	private QnaService qnaService;
	
	@Before
	public void setup(){
		qnaService = new QnaService(questionDao, answerDao);
	}
	
	@Test(expected = CannotDeleteException.class)
	public void deleteQuestion_NoneQuestion() throws Exception {
		when(questionDao.findById(1L)).thenReturn(null);
		
		qnaService.deleteQuestion(1L, newUser("userId"));
	}
	
	@Test
	public void deleteQuestion_sameUser_noneAnswer() throws Exception{
		Question question = newQuestion(1L, "test");
		when(questionDao.findById(1L)).thenReturn(question);
		when(answerDao.findAllByQuestionId(1L)).thenReturn(Lists.newArrayList());
		
		qnaService.deleteQuestion(1L, newUser("test"));
	}
}

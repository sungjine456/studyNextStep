package org.studyStepNext.part9.next.service;

import java.util.List;

import org.studyStepNext.part9.next.CannotDeleteException;
import org.studyStepNext.part9.next.dao.AnswerDao;
import org.studyStepNext.part9.next.dao.QuestionDao;
import org.studyStepNext.part9.next.model.Answer;
import org.studyStepNext.part9.next.model.Question;
import org.studyStepNext.part9.next.model.User;

public class QnaService {
	private static QnaService qnaService = new QnaService();
	
	private QuestionDao questionDao = QuestionDao.getInstance();
	private AnswerDao answerDao = AnswerDao.getInstance();
	
	private QnaService(){}
	public static QnaService getInstance(){
		return qnaService;
	}
	
	public void deleteQuestion(long questionId, User user) throws CannotDeleteException {
		Question question = questionDao.findById(questionId);
		if(question == null){
			throw new CannotDeleteException("존재하지 않는 질문입니다.");
		}
		if(question.getWriter().equals(user.getUserId())){
			throw new CannotDeleteException("다른 사용자가 쓴 글은 삭제할 수 없습니다.");
		}
		List<Answer> answers = answerDao.findAllByQuestionId(questionId);
		if(answers.isEmpty()){
			questionDao.delete(questionId);
			return;
		}
		boolean canDelete = true;
		for(Answer answer : answers){
			String writer = question.getWriter();
			if(!writer.equals(answer.getWriter())){
				canDelete = false;
				break;
			}
		}
		if(!canDelete){
			throw new CannotDeleteException("다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다.");
		}
		questionDao.delete(questionId);
	}
}

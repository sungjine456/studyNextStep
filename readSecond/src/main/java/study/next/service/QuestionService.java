package study.next.service;

import java.util.List;

import study.next.dao.AnswerDao;
import study.next.dao.QuestionDao;
import study.next.exception.CannotDeleteException;
import study.next.model.Answer;
import study.next.model.Question;
import study.next.model.User;

public class QuestionService {
	private static QuestionService questionService;
	
	private QuestionService(){}
	
	public static QuestionService getInstance(){
		if(questionService == null){
			questionService = new QuestionService();
		}
		return questionService;
	}
	
	private QuestionDao questionDao = QuestionDao.getInstance();
	private AnswerDao answerDao = AnswerDao.getInstance();
	
	public void deleteQuestion(long questionId, User writer){
		Question question = questionDao.findByQuestionId(questionId);
		if(!writer.equals(question.getWriter())){
			throw new CannotDeleteException("삭제하는 유저가 질문한 유저와 다르다.");
		}
		
		List<Answer> answers = answerDao.findAllByQuestionId(questionId);
		long count = answers.stream().filter(answer -> !writer.getName().equals(answer.getWriter())).count();
		if(count != 0){
			throw new CannotDeleteException("삭제하는 유저와 다른 답변한 유저가 있다.");
		}
		
		questionDao.delete(questionId);
	}
}

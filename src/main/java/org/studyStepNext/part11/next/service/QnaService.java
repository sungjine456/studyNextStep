package org.studyStepNext.part11.next.service;

import java.util.List;

import org.studyStepNext.part11.next.CannotDeleteException;
import org.studyStepNext.part11.next.dao.AnswerDao;
import org.studyStepNext.part11.next.dao.QuestionDao;
import org.studyStepNext.part11.next.model.Answer;
import org.studyStepNext.part11.next.model.Question;
import org.studyStepNext.part11.next.model.User;

public class QnaService {
    private QuestionDao questionDao;
    private AnswerDao answerDao;

    public QnaService(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    public Question findById(long questionId) {
        return questionDao.findById(questionId);
    }

    public List<Answer> findAllByQuestionId(long questionId) {
        return answerDao.findAllByQuestionId(questionId);
    }

    public void deleteQuestion(long questionId, User user) throws CannotDeleteException {
        Question question = questionDao.findById(questionId);
        if (question == null) {
            throw new CannotDeleteException("존재하지 않는 질문입니다.");
        }

        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        if (question.canDelete(user, answers)) {
            questionDao.delete(questionId);
        }
    }
}

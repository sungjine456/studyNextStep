package study.next.dao;

import java.sql.ResultSet;
import java.util.List;

import study.core.jdbc.JdbcTemplate;
import study.next.model.Answer;

public class AnswerDao {
	public List<Answer> findAllByQuestionId(long questionId) {
		JdbcTemplate<Answer> selectJdbcTemplate = new JdbcTemplate<Answer>();
		return selectJdbcTemplate.query("SELECT answerId, writer, contents, createdDate FROM ANSWERS WHERE questionId = ? "
                + "order by answerId desc",(ResultSet rs)->{
            return new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                    rs.getDate("createdDate"), questionId);
		}, questionId);
	}
	
	public Answer insert(Answer answer){
		JdbcTemplate<Answer> insertJdbcTemplate = new JdbcTemplate<Answer>();
		insertJdbcTemplate.update("INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)"
							, answer.getWriter(), answer.getContents(), answer.getCreatedDate(), answer.getQuestionId());
		return answer;
	}
	
	public void delete(long id){
		
	}
}

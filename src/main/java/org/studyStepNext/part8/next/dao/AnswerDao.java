package org.studyStepNext.part8.next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.studyStepNext.part8.core.jdbc.JdbcTemplate;
import org.studyStepNext.part8.core.jdbc.KeyHolder;
import org.studyStepNext.part8.core.jdbc.RowMapper;
import org.studyStepNext.part8.next.model.Answer;

public class AnswerDao {
	public Answer insert(Answer answer){
		JdbcTemplate jt = new JdbcTemplate();
		String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
        jt.update(sql, answer.getWriter(), answer.getContents(), new Timestamp(answer.getCreatedDate().getTime()), 
        		answer.getQuestionId(), new KeyHolder());
        Answer a = findById(answer.getAnswerId());
        System.out.println(a);
        return a;
	}
	
	public Answer findById(int answerId){
		JdbcTemplate jt = new JdbcTemplate();
		String sql = "SELECT * FROM ANSWERS WHERE ANSWERID = ?";
		RowMapper<Answer> rm = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs) throws SQLException {
                return new Answer(rs.getInt("answerId"), rs.getString("writer"), rs.getString("contents"), 
                		rs.getTimestamp("createdDate"), rs.getInt("questionId"));
            }
        };
		return jt.queryForObject(sql, rm, answerId);
	}
	
	public List<Answer> findAllByQuestionId(int questionId){
		JdbcTemplate jt = new JdbcTemplate();
		String sql = "SELECT * FROM ANSWERS WHERE QUESTIONID = ?";
		RowMapper<Answer> rm = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs) throws SQLException {
                return new Answer(rs.getInt("answerId"), rs.getString("writer"), rs.getString("contents"), 
                		rs.getTimestamp("createdDate"), rs.getInt("questionId"));
            }
        };
		return jt.query(sql, rm, questionId);
	}
}

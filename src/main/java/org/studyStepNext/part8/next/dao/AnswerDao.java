package org.studyStepNext.part8.next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.studyStepNext.part8.core.jdbc.JdbcTemplate;
import org.studyStepNext.part8.core.jdbc.KeyHolder;
import org.studyStepNext.part8.core.jdbc.PreparedStatementCreator;
import org.studyStepNext.part8.core.jdbc.RowMapper;
import org.studyStepNext.part8.next.model.Answer;

public class AnswerDao {
	public Answer insert(Answer answer){
		JdbcTemplate jt = new JdbcTemplate();
		String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
		PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, answer.getWriter());
                pstmt.setString(2, answer.getContents());
                pstmt.setTimestamp(3, new Timestamp(answer.getCreatedDate().getTime()));
                pstmt.setInt(4, answer.getQuestionId());
                return pstmt;
            }
        };
        KeyHolder key = new KeyHolder();
        jt.update(psc, key);
        return findById(key.getId());
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

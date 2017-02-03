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
import org.studyStepNext.part8.next.model.Question;

public class QuestionDao {
	public Question insert(Question question){
		JdbcTemplate jt = new JdbcTemplate();
		String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?)";
		PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, question.getWriter());
                pstmt.setString(2, question.getTitle());
                pstmt.setString(3, question.getContents());
                pstmt.setTimestamp(4, new Timestamp(question.getCreatedDate().getTime()));
                pstmt.setInt(5, question.getCountOfAnswer());
                return pstmt;
            }
        };
        KeyHolder key = new KeyHolder();
        jt.update(psc, key);
        return findById(key.getId());
	}
	
	public Question findById(int questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT * FROM QUESTIONS WHERE questionId = ?";
		RowMapper<Question> rm = new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs) throws SQLException {
                return new Question(rs.getInt("questionId"), rs.getString("writer"), rs.getString("title"), 
                		rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
            }
        };
		return jdbcTemplate.queryForObject(sql, rm, questionId);
	}
	
	public List<Question> findAll() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT * FROM QUESTIONS";
		RowMapper<Question> rm = new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs) throws SQLException {
                return new Question(rs.getInt("questionId"), rs.getString("writer"), rs.getString("title"), 
                		rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
            }
        };
		return jdbcTemplate.query(sql, rm);
	}
}

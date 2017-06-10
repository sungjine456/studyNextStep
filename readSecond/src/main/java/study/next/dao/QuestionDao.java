package study.next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import study.core.jdbc.JdbcTemplate;
import study.next.model.Question;

public class QuestionDao {
	public Question findByQuestionId(long questionId) throws SQLException {
		JdbcTemplate<Question> selectJdbcTemplate = new JdbcTemplate<Question>();
    	return selectJdbcTemplate.queryForObject("SELECT questionId, writer, title, contents, createdDate, countOfAnswer"
    			+ " FROM QUESTIONS WHERE questionId=?",(ResultSet rs)->{
            return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
                    rs.getString("contents"), rs.getDate("createdDate"), rs.getInt("countOfAnswer"));
    	}, questionId);
    }

	public List<Question> findAll() {
		JdbcTemplate<Question> selectJdbcTemplate = new JdbcTemplate<Question>();
    	return selectJdbcTemplate.query("SELECT questionId, writer, title, contents, createdDate, countOfAnswer"
    			+ " FROM QUESTIONS",(ResultSet rs)->{
            return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
                    rs.getString("contents"), rs.getDate("createdDate"), rs.getInt("countOfAnswer"));
    	});
	}
}

package study.next.dao;

import java.sql.ResultSet;
import java.util.List;

import study.core.jdbc.JdbcTemplate;
import study.next.model.Answer;

public class AnswerDao {
	private JdbcTemplate<Answer> jdbcTemplate = JdbcTemplate.getInstance();
	
	public Answer findByAnswerId(long answerId) {
		return jdbcTemplate.queryForObject("SELECT writer, questionId, contents, createdDate FROM ANSWERS WHERE answerId = ? "
                + "order by answerId desc",(ResultSet rs)->{
            return new Answer(answerId, rs.getString("writer"), rs.getString("contents"),
                    rs.getDate("createdDate"), rs.getLong("questionId"));
		}, answerId);
	}
	
	public List<Answer> findAllByQuestionId(long questionId) {
		return jdbcTemplate.query("SELECT answerId, writer, contents, createdDate FROM ANSWERS WHERE questionId = ? "
                + "order by answerId desc",(ResultSet rs)->{
            return new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                    rs.getDate("createdDate"), questionId);
		}, questionId);
	}
	
	public Answer insert(Answer answer){
		jdbcTemplate.update("INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)"
							, answer.getWriter(), answer.getContents(), answer.getCreatedDate(), answer.getQuestionId());
		return answer;
	}
	
	public void delete(long id){
        String sql = "DELETE FROM ANSWERS WHERE answerId = ?";
        jdbcTemplate.update(sql, id);
	}
}

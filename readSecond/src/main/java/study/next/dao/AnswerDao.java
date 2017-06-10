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
}

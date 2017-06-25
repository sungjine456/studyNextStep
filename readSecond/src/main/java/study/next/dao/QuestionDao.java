package study.next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;

import study.core.jdbc.JdbcTemplate;
import study.core.jdbc.KeyHolder;
import study.next.model.Question;
import study.next.model.User;

public class QuestionDao {
	
	private static QuestionDao questionDao = new QuestionDao();

	public static QuestionDao getInstance(){
		if (questionDao == null) {
			questionDao = new QuestionDao();
        }
		return questionDao;
	}
	
	private QuestionDao(){}
	
	private JdbcTemplate<Question> jdbcTemplate = JdbcTemplate.getInstance();
	private UserDao userDao = UserDao.getInstance();
	
	public Question findByQuestionId(long questionId) {
    	return jdbcTemplate.queryForObject("SELECT questionId, writer, title, contents, createdDate, countOfAnswer"
    			+ " FROM QUESTIONS WHERE questionId=?",(ResultSet rs)->{
    		User writer = userDao.findByUserId(rs.getString("writer"));
            return new Question(rs.getLong("questionId"), writer, rs.getString("title"),
                    rs.getString("contents"), rs.getDate("createdDate"), rs.getInt("countOfAnswer"));
    	}, questionId);
    }

	public List<Question> findAll() {
    	return jdbcTemplate.query("SELECT questionId, writer, title, contents, createdDate, countOfAnswer"
    			+ " FROM QUESTIONS",(ResultSet rs)->{
    		User writer = userDao.findByUserId(rs.getString("writer"));
            return new Question(rs.getLong("questionId"), writer, rs.getString("title"),
                    rs.getString("contents"), rs.getDate("createdDate"), rs.getInt("countOfAnswer"));
    	});
	}
	
	public Question insert(Question question) {
        String sql = "INSERT INTO QUESTIONS " + 
                "(writer, title, contents, createdDate) " + 
                " VALUES (?, ?, ?, ?)";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, question.getWriter().getUserId());
                pstmt.setString(2, question.getTitle());
                pstmt.setString(3, question.getContents());
                pstmt.setTimestamp(4, new Timestamp(question.getCreatedDate().getTime()));
                return pstmt;
            }
        };

        KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return findByQuestionId(keyHolder.getId());
    }
	
	public void update(Question question) {
        String sql = "UPDATE QUESTIONS set title = ?, contents = ? WHERE questionId = ?";
        jdbcTemplate.update(sql, question.getTitle(), question.getContents(), question.getQuestionId());
    }
	
	public void delete(long questionId) {
		String sql = "DELETE FROM QUESTIONS WHERE questionId = ?";
		jdbcTemplate.update(sql, questionId);
	}
	
	public void increaseCountOfAnswer(long questionId) {
        String sql = "UPDATE QUESTIONS set countOfAnswer = countOfAnswer + 1 WHERE questionId = ?";
        jdbcTemplate.update(sql, questionId);
    }

	public void decreaseCountOfAnswer(long questionId) {
		String sql = "UPDATE QUESTIONS set countOfAnswer = countOfAnswer - 1 WHERE questionId = ?";
		jdbcTemplate.update(sql, questionId);
	}
}

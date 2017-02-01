package org.studyStepNext.part7.next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.studyStepNext.part7.next.model.User;

public class UserDao {
    public void insert(User user) {
    	JdbcTemplate jt = new JdbcTemplate();
        jt.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", user.getUserId(), user.getPassword(), 
        		user.getName(), user.getEmail());
    }

    public void update(User user) {
    	JdbcTemplate jt = new JdbcTemplate();
		jt.update("UPDATE Users SET userId = ?, password = ?, name = ?, email = ? where userId = ?", 
				user.getUserId(), user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public List<User> findAll() {
    	JdbcTemplate jt = new JdbcTemplate();
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement pstmt) throws SQLException {
			}
		};
		RowMapper<User> rowMapper = new RowMapper<User>() {
			public User mapRow(ResultSet rs) throws SQLException {
				return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
			}
		};
		return jt.query("SELECT userId, password, name, email FROM USERS", pss, rowMapper);
    }

    public User findByUserId(String userId) {
    	JdbcTemplate jt = new JdbcTemplate();
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
		return jt.queryForObject(sql, (ResultSet rs)->{
			return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), 
					rs.getString("email"));
    	}, userId);
    }
}

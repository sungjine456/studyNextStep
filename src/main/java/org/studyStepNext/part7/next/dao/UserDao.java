package org.studyStepNext.part7.next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.studyStepNext.part7.next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
    	JdbcTemplate jt = new JdbcTemplate();
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
        	public void setValues(PreparedStatement pstmt) throws SQLException{
    	    	pstmt.setString(1, user.getUserId());
    	        pstmt.setString(2, user.getPassword());
    	        pstmt.setString(3, user.getName());
    	        pstmt.setString(4, user.getEmail());
    	    }
        };
        jt.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", pss);
    }

    public void update(User user) throws SQLException {
    	JdbcTemplate jt = new JdbcTemplate();
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement pstmt) throws SQLException{
				pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getName());
				pstmt.setString(4, user.getEmail());
				pstmt.setString(5, user.getUserId());
			}
		};
		jt.update("UPDATE Users SET userId = ?, password = ?, name = ?, email = ? where userId = ?", pss);
    }

    public List<User> findAll() throws SQLException {
    	JdbcTemplate jt = new JdbcTemplate();
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement pstmt) throws SQLException {
			}
		};
		RowMapper rowMapper = new RowMapper() {
			public Object mapRow(ResultSet rs) throws SQLException {
				return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
			}
		};
		return jt.query("SELECT userId, password, name, email FROM USERS", pss, rowMapper);
    }

    public User findByUserId(String userId) throws SQLException {
    	JdbcTemplate jt = new JdbcTemplate();
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, userId);
			}
		};
		RowMapper rowMapper = new RowMapper() {
			public Object mapRow(ResultSet rs) throws SQLException {
				return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
			}
		};
		return jt.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userId = ?", pss, rowMapper);
    }
}

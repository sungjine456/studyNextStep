package study.next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import study.core.jdbc.JdbcTemplate;
import study.core.jdbc.PreparedStatementSetter;
import study.core.jdbc.RowMapper;
import study.next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
    	JdbcTemplate insertJdbcTemplate = new JdbcTemplate();
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
        	public void setValues(PreparedStatement pstmt) throws SQLException {
            	pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
    	};
        insertJdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)",pss);
    }
    
    public void update(User user) throws SQLException {
    	JdbcTemplate updateJdbcTemplate = new JdbcTemplate();
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
    		public void setValues(PreparedStatement pstmt) throws SQLException {
    	    	pstmt.setString(1, user.getPassword());
    	        pstmt.setString(2, user.getName());
    	        pstmt.setString(3, user.getEmail());
    	        pstmt.setString(4, user.getUserId());
    	    }
    	};
    	updateJdbcTemplate.update("UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?", pss);
    }

    public List<User> findAll() throws SQLException {
    	JdbcTemplate selectJdbcTemplate = new JdbcTemplate();
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
			}
    	};
    	RowMapper<List<User>> rowMapper = new RowMapper<List<User>>(){
			@Override
			public List<User> mapRow(ResultSet rs) throws SQLException {
				List<User> users = new ArrayList<>();
	            if (rs.next()) {
	                users.add(new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
	                        rs.getString("email")));
	            }
	            return users;
			}
		};
		return selectJdbcTemplate.query("SELECT userId, password, name, email FROM USERS",pss,rowMapper);
    }

    public User findByUserId(String userId) throws SQLException {
    	JdbcTemplate selectJdbcTemplate = new JdbcTemplate();
    	PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
	            pstmt.setString(1, userId);
			}
    	};
    	RowMapper<User> rowMapper = new RowMapper<User>(){
			@Override
			public User mapRow(ResultSet rs) throws SQLException {
	            User user = null;
	            if (rs.next()) {
	                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
	                        rs.getString("email"));
	            }
	            return user;
			}
    	};
    	return selectJdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?",pss,rowMapper);
    }
}

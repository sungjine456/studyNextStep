package study.next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import study.core.jdbc.JdbcTemplate;
import study.next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
    	JdbcTemplate insertJdbcTemplate = new JdbcTemplate();
        insertJdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", 
        		user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }
    
    public void update(User user) throws SQLException {
    	JdbcTemplate updateJdbcTemplate = new JdbcTemplate();
    	updateJdbcTemplate.update("UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?",
    			user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public List<User> findAll() throws SQLException {
    	JdbcTemplate selectJdbcTemplate = new JdbcTemplate();
		return selectJdbcTemplate.query("SELECT userId, password, name, email FROM USERS",(ResultSet rs)->{
			List<User> users = new ArrayList<>();
            if (rs.next()) {
                users.add(new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email")));
            }
            return users;
		});
    }

    public User findByUserId(String userId) throws SQLException {
    	JdbcTemplate selectJdbcTemplate = new JdbcTemplate();
    	return selectJdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?",(ResultSet rs)->{
    		User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }
            return user;
    	},userId);
    }
}

package study.next.dao;

import java.sql.ResultSet;
import java.util.List;

import study.core.jdbc.JdbcTemplate;
import study.next.model.User;

public class UserDao {
	private  JdbcTemplate<User> jdbcTemplate = JdbcTemplate.getInstance();
	
    public void insert(User user) {
    	jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", 
        		user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }
    
    public void update(User user) {
    	jdbcTemplate.update("UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?",
    			user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public List<User> findAll() {
		return jdbcTemplate.query("SELECT userId, password, name, email FROM USERS",(ResultSet rs)->{
            return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                    rs.getString("email"));
		});
    }

    public User findByUserId(String userId) {
    	return jdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?",(ResultSet rs)->{
            return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                    rs.getString("email"));
    	},userId);
    }
}

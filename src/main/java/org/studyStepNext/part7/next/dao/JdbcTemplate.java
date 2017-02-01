package org.studyStepNext.part7.next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.studyStepNext.part7.core.jdbc.ConnectionManager;
import org.studyStepNext.part7.next.model.User;

public class JdbcTemplate {
	public void update(String sql, PreparedStatementSetter pss) throws DataAccessException {
        try(Connection con = ConnectionManager.getConnection();
        		PreparedStatement pstmt = con.prepareStatement(sql)) {
            pss.setValues(pstmt);
            pstmt.executeUpdate();
        } catch(SQLException e) {
        	throw new DataAccessException(e);
        }
	}
	List<User> query(String sql, PreparedStatementSetter pss, RowMapper rowMapper) throws DataAccessException {
		ResultSet rs = null;
        try(Connection con = ConnectionManager.getConnection();
        		PreparedStatement pstmt = con.prepareStatement(sql);) {
            pss.setValues(pstmt);
            rs = pstmt.executeQuery();
            List<User> users = new ArrayList<User>();
            if (rs.next()) {
                users.add((User) rowMapper.mapRow(rs));
            }
            return users;
        } catch(SQLException e) {
        	throw new DataAccessException(e);
        } finally {
        	if (rs != null) {
                try {
					rs.close();
				} catch (SQLException e) {
					throw new DataAccessException(e);
				}
            }
        }
	}
	User queryForObject(String sql, PreparedStatementSetter pss, RowMapper rowMapper) {
		List<User> users = query(sql, pss, rowMapper);
		if(users.isEmpty()){
			return null;
		}
		return users.get(0);
	}
}

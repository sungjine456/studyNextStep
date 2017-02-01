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
	public void update(String sql, PreparedStatementSetter pss) throws SQLException {
		Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setValues(pstmt);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
	}
	List<User> query(String sql, PreparedStatementSetter pss, RowMapper rowMapper) throws SQLException {
		Connection con = null;
    	PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setValues(pstmt);
            
            rs = pstmt.executeQuery();

            List<User> users = new ArrayList<User>();
            if (rs.next()) {
                users.add((User) rowMapper.mapRow(rs));
            }

            return users;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
	}
	User queryForObject(String sql, PreparedStatementSetter pss, RowMapper rowMapper) throws SQLException{
		List<User> users = query(sql, pss, rowMapper);
		if(users.isEmpty()){
			return null;
		}
		return users.get(0);
	}
}

package study.core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import study.next.model.User;

public abstract class SelectJdbcTemplate {
	@SuppressWarnings("unchecked")
	public List<User> query(String query) throws SQLException {
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = setValue(con.prepareStatement(query));
            return (List<User>)mapRow(pstmt.executeQuery());
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
	
	public User queryForObject(String query) throws SQLException {
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = setValue(con.prepareStatement(query));
            return (User)mapRow(pstmt.executeQuery());
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
	
	public abstract PreparedStatement setValue(PreparedStatement pstmt) throws SQLException;
	public abstract Object mapRow(ResultSet rs)  throws SQLException;
}

package study.core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import study.next.model.User;

public class JdbcTemplate {
	public void update(String query, PreparedStatementSetter pss) throws DataAccessException {
        try(Connection con = ConnectionManager.getConnection();
        		PreparedStatement pstmt = con.prepareStatement(query)) {
            pss.setValues(pstmt);
            
            pstmt.executeUpdate();
        } catch(SQLException e){
        	throw new DataAccessException(e);
        }
	}
	
	public List<User> query(String query, PreparedStatementSetter pss, RowMapper<List<User>> rowMapper) throws DataAccessException {
        ResultSet rs = null;
        try(Connection con = ConnectionManager.getConnection();
        		PreparedStatement pstmt = con.prepareStatement(query)) {
            pss.setValues(pstmt);
            
            rs = pstmt.executeQuery();
            
            return (List<User>)rowMapper.mapRow(rs);
        } catch(SQLException e){
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
	
	public User queryForObject(String query, PreparedStatementSetter pss, RowMapper<User> rowMapper) throws DataAccessException {
        ResultSet rs = null;
        try(Connection con = ConnectionManager.getConnection();
        		PreparedStatement pstmt = con.prepareStatement(query)) {
            pss.setValues(pstmt);
            
            rs = pstmt.executeQuery();
            
            return (User) rowMapper.mapRow(rs);
        } catch(SQLException e){
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
}

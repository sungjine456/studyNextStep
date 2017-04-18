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
	
	public void update(String query, Object...values){
		update(query, createPreparedStatementSetter(values));
	}
	
	public List<User> query(String query, RowMapper<List<User>> rowMapper, PreparedStatementSetter pss) throws DataAccessException {
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
	
	public List<User> query(String query, RowMapper<List<User>> rowMapper, Object...values) throws DataAccessException {
		return query(query, rowMapper, createPreparedStatementSetter(values));
	}
	
	public User queryForObject(String query, RowMapper<User> rowMapper, PreparedStatementSetter pss) throws DataAccessException {
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
	
	public User queryForObject(String query, RowMapper<User> rowMapper, Object...values) throws DataAccessException {
		return queryForObject(query, rowMapper, createPreparedStatementSetter(values));
	}
	
	private PreparedStatementSetter createPreparedStatementSetter(Object...objects){
		PreparedStatementSetter pstmts = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				for(int i = 0; i < objects.length; i++){
					pstmt.setObject(i+1, objects[i]);
				}
			}
		};
		return pstmts;
	}
}

package org.studyStepNext.part7.next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.studyStepNext.part7.core.jdbc.ConnectionManager;

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
	public void update(String sql, Object... parameters) throws DataAccessException {
        try(Connection con = ConnectionManager.getConnection();
        		PreparedStatement pstmt = con.prepareStatement(sql)) {
            for(int i = 0; i < parameters.length; i++){
            	pstmt.setObject(i+1, parameters[i]);
            }
            pstmt.executeUpdate();
        } catch(SQLException e) {
        	throw new DataAccessException(e);
        }
	}
	public <T> List<T> query(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper) throws DataAccessException {
		ResultSet rs = null;
        try(Connection con = ConnectionManager.getConnection();
        		PreparedStatement pstmt = con.prepareStatement(sql);) {
            pss.setValues(pstmt);
            rs = pstmt.executeQuery();
            List<T> users = new ArrayList<T>();
            if (rs.next()) {
                users.add(rowMapper.mapRow(rs));
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
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... parameters) {
		List<T> users = query(sql, rowMapper, parameters);
		if(users.isEmpty()){
			return null;
		}
		return users.get(0);
	}
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
        return query(sql, rowMapper, createPreparedStatementSetter(parameters));
    }
    private PreparedStatementSetter createPreparedStatementSetter(Object... parameters) {
        return new PreparedStatementSetter() {
			public void setValues(PreparedStatement pstmt) throws SQLException {
				for (int i = 0; i < parameters.length; i++) {
                    pstmt.setObject(i + 1, parameters[i]);
                }
			}
        };
    }
}

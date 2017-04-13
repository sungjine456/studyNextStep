package study.core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcTemplate {
	public void update(String query) throws SQLException {
		Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = query;
            setValuesFor(con.prepareStatement(sql));
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
	}
	
	public abstract void setValuesFor(PreparedStatement pstmt) throws SQLException;
}

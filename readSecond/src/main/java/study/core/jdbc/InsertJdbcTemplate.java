package study.core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import study.next.dao.UserDao;
import study.next.model.User;

public class InsertJdbcTemplate {
	public void insert(User user, UserDao userDao) throws SQLException {
		Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = userDao.createQueryForInsert();
            userDao.setValuesForInsert(user, con.prepareStatement(sql));
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
	}
}

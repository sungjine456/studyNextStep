package org.studyStepNext.part7.next.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementSetter {
	void setValues(PreparedStatement pstmt) throws SQLException;
}

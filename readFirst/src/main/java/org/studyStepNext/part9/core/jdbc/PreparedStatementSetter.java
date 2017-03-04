package org.studyStepNext.part9.core.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementSetter {
    void setParameters(PreparedStatement pstmt) throws SQLException;
}

package org.studyStepNext.part7.next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper {
	Object mapRow(ResultSet rs) throws SQLException;
}
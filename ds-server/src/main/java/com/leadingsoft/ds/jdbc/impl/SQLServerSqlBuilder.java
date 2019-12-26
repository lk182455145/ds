package com.leadingsoft.ds.jdbc.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.leadingsoft.ds.dto.DbConnectionDto.DBType;
import com.leadingsoft.ds.jdbc.SqlBuilder;

@Component
public class SQLServerSqlBuilder extends AbstractSqlBuilder implements SqlBuilder, InitializingBean {

	@Override
	protected char getColumnSplitCharStart() {
		return '[';
	}

	@Override
	protected char getColumnSplitCharEnd() {
		return ']';
	}

	@Override
	protected DBType getDbType() {
		return DBType.SQLSERVER;
	}

	@Override
	protected String buildPagedSql(String sql, Pageable pageable) {
		StringBuilder builder = new StringBuilder(sql);
		builder.append(" OFFSET ").append(pageable.getOffset()).append(" ROWS FETCH NEXT ")
				.append(pageable.getPageSize()).append(" ROWS ONLY");
		return builder.toString();
	}
}

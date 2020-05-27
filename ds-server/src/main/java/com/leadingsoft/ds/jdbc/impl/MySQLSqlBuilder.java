package com.leadingsoft.ds.jdbc.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.leadingsoft.ds.dto.DbConnectionDto.DBType;
import com.leadingsoft.ds.jdbc.SqlBuilder;

@Component
public class MySQLSqlBuilder extends AbstractSqlBuilder implements SqlBuilder, InitializingBean {

	@Override
	protected char getColumnSplitCharStart() {
		return '`';
	}

	@Override
	protected char getColumnSplitCharEnd() {
		return '`';
	}

	@Override
	protected DBType getDbType() {
		return DBType.MYSQL;
	}

	@Override
	protected String buildPagedSql(StringBuilder builder, Pageable pageable) {
		builder.append(" limit ").append(pageable.getOffset()).append(",").append(pageable.getPageSize());
		return builder.toString();
	}
}

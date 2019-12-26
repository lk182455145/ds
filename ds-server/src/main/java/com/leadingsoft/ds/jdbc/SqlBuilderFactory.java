package com.leadingsoft.ds.jdbc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.leadingsoft.ds.dto.DbConnectionDto.DBType;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SqlBuilderFactory {

	private final Map<DBType, SqlBuilder> map = new HashMap<>();

	/**
	 * 根据数据类型，获取Sql构建器
	 * 
	 * @param type
	 * @return
	 */
	public SqlBuilder getBuilder(DBType type) {
		return map.get(type);
	}

	public void register(DBType type, SqlBuilder builder) {
		log.info("Register a sqlBuilder [" + builder + "] on type [" + type + "]");
		map.put(type, builder);
	}

}

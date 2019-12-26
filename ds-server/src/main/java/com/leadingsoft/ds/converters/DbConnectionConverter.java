package com.leadingsoft.ds.converters;

import org.springframework.stereotype.Component;

import com.dm.common.converter.Converter;
import com.leadingsoft.ds.dto.DbConnectionDto;
import com.leadingsoft.ds.entities.DbConnection;

@Component
public class DbConnectionConverter implements Converter<DbConnection, DbConnectionDto> {

	@Override
	public DbConnectionDto toDto(DbConnection model) {
		DbConnectionDto dto = new DbConnectionDto();
		dto.setDbType(model.getDbType());
		dto.setId(model.getId());
		dto.setPassword(model.getPassword());
		dto.setUrl(model.getUrl());
		dto.setUsername(model.getUsername());
		dto.setName(model.getName());
		return dto;
	}

	@Override
	public DbConnection copyProperties(DbConnection model, DbConnectionDto dto) {
		model.setDbType(dto.getDbType());
		model.setUrl(dto.getUrl());
		model.setName(dto.getName());
		model.setUsername(dto.getUsername());
		model.setPassword(dto.getPassword());
		return model;
	}

}

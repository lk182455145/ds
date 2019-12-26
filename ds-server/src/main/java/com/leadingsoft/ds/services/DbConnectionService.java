package com.leadingsoft.ds.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.leadingsoft.ds.dto.DbConnectionDto;
import com.leadingsoft.ds.entities.DbConnection;

public interface DbConnectionService extends CrudService<DbConnection, DbConnectionDto, Long> {

	public Page<DbConnection> list(Pageable pageable);

	public List<DbConnection> listAll();
}

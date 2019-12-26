package com.leadingsoft.ds.jdbc;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import com.leadingsoft.ds.entities.Svc;

public interface SqlBuilder {

	
	public QuerySql getQuerySql(Svc svc, MultiValueMap<String, String> params, Pageable pageable);

}

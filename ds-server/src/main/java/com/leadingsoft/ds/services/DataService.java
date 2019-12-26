package com.leadingsoft.ds.services;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import com.leadingsoft.ds.dto.QueryResult;

public interface DataService {

	QueryResult queryData(String service, MultiValueMap<String, String> params, Pageable pageable);

}

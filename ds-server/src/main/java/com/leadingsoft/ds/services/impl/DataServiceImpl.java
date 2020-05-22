package com.leadingsoft.ds.services.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.leadingsoft.ds.dto.DbConnectionDto.DBType;
import com.leadingsoft.ds.entities.DbConnection;
import com.leadingsoft.ds.entities.Svc;
import com.leadingsoft.ds.jdbc.QuerySql;
import com.leadingsoft.ds.jdbc.SqlBuilder;
import com.leadingsoft.ds.jdbc.SqlBuilderFactory;
import com.leadingsoft.ds.services.DataService;
import com.leadingsoft.ds.services.DataSourceService;
import com.leadingsoft.ds.services.SvcService;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private SvcService svcService;

    @Autowired
    private SqlBuilderFactory sqlBuilderFactory;

    @Autowired
    private DataSourceService dataSourceService;

    @Override
    @Cacheable(cacheNames = { "resultCache" }, sync = true)
    public Page<Map<String, Object>> queryData(String service, MultiValueMap<String, String> params,
            Pageable pageable) {
        Svc svc = svcService.findByName(service);
        DbConnection cnn = svc.getConnection();
        DBType type = cnn.getDbType();
        DataSource dataSource = dataSourceService.get(cnn);
        // 根据数据库类型选择SQL构建器
        SqlBuilder sqlBuilder = sqlBuilderFactory.getBuilder(type);
        // 构建查询语句
        QuerySql querySql = sqlBuilder.getQuerySql(svc, params, pageable);
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Long count = jdbcTemplate.queryForObject(querySql.getCountSql(), querySql.getParameters(), Long.class);
        List<Map<String, Object>> content = jdbcTemplate.queryForList(querySql.getQuerySql(), querySql.getParameters());
        return new PageImpl<>(content, pageable, count);
    }

}

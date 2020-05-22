package com.leadingsoft.ds.jdbc.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.util.MultiValueMap;

import com.leadingsoft.ds.dto.DbConnectionDto.DBType;
import com.leadingsoft.ds.entities.Order;
import com.leadingsoft.ds.entities.Parameter;
import com.leadingsoft.ds.entities.Svc;
import com.leadingsoft.ds.jdbc.QuerySql;
import com.leadingsoft.ds.jdbc.SqlBuilder;
import com.leadingsoft.ds.jdbc.SqlBuilderFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractSqlBuilder implements SqlBuilder, InitializingBean {

    private final String SPACE = " ";

    @Autowired
    private SqlBuilderFactory factory;

    /**
     * 获取支持的数据库的类型
     * 
     * @return
     */
    protected abstract DBType getDbType();

    /**
     * 获取列名称分割字符
     * 
     * @return
     */
    protected abstract char getColumnSplitCharStart();

    /**
     * 获取列名称分割字符
     * 
     * @return
     */
    protected abstract char getColumnSplitCharEnd();

    @Override
    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        factory.register(getDbType(), this);
    }

    private QuerySql builderSql(Svc svc, MultiValueMap<String, String> params, Pageable pageable) {

        String sql = svc.getSql();
        StringBuilder countBuilder = new StringBuilder("SELECT count(*) count from (").append(sql)
                .append(") t");
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM (").append(sql).append(") t");

        List<Parameter> parameters = svc.getParameters();
        List<Parameter> paramToUse = new ArrayList<>();
        // 查询实际使用的参数列表
        Map<String, Object> parameterMap = new HashMap<>();

        // 实际传入的参数列表
        Set<String> keys = params.keySet();

        // 分析实际用到的参数列表
        for (Parameter parameter : parameters) {
            String paraName = parameter.getParameterName();
            if (keys.contains(paraName)) {
                paramToUse.add(parameter);
            }
        }
        // 添加SQL语句内部的命名参数到参数列表
        addPreparedParameters(sql, parameterMap, params);

        // 分析实际使用的参数列表，并添加到SQL语句的参数列表中
        if (CollectionUtils.isNotEmpty(paramToUse)) {
            StringBuilder wBuilder = new StringBuilder();
            Iterator<Parameter> pi = paramToUse.iterator();
            wBuilder.append(" WHERE ");
            while (pi.hasNext()) {
                Parameter pa = pi.next();
                List<String> paValue = params.get(pa.getParameterName());
                if ("in".equals(pa.getOperator())) {
                    addInParameters(wBuilder, parameterMap, pa, paValue);
                } else {
                    String paramValue = paValue.get(0);
                    // 处理like语法的参数
                    if ("like".equals(pa.getOperator())) {
                        String val = String.valueOf(paramValue);
                        val = val.replace("[", "[[]");
                        val = val.replace("%", "[%]");
                        val = val.replace("_", "[_]");
                        paramValue = val = "%" + val + "%";
                    }
                    parameterMap.put(pa.getParameterName(), new SqlParameterValue(Types.VARCHAR, paramValue));
                    wBuilder.append(getColumnSplitCharStart()).append(pa.getColumn()).append(getColumnSplitCharEnd())
                            .append(SPACE)
                            .append(pa.getOperator()).append(" :")
                            .append(pa.getParameterName());
                }

                if (pi.hasNext()) {
                    wBuilder.append(" AND ");
                }
            }
            queryBuilder.append(wBuilder);
            countBuilder.append(wBuilder);
        }

        String countSql = countBuilder.toString();
        log.debug("Create count sql {" + countSql + "}");

        // 添加排序语句
        queryBuilder.append(" ORDER BY ");
        List<Order> orders = svc.getOrders();
        Iterator<Order> io = orders.iterator();
        while (io.hasNext()) {
            Order order = io.next();
            queryBuilder.append(getColumnSplitCharStart()).append(order.getColumn()).append(getColumnSplitCharEnd())
                    .append(' ').append(order.getDirection().toString());
            if (io.hasNext()) {
                queryBuilder.append(",");
            }
        }

        // 添加分页语句

        String pagedSql = buildPagedSql(queryBuilder.toString(), pageable);

        QuerySqlImpl querySql = new QuerySqlImpl(countBuilder.toString(), pagedSql, parameterMap);
        return querySql;
    }

    protected abstract String buildPagedSql(String string, Pageable pageable);

    /**
     * 添加条件符号为in的参数相关的语句和参数列表
     * 
     * @param wBuilder        条件语句构建器
     * @param parameterTarget 存放参数的目标
     * @param pa              参数配置信息
     * @param paValue         参数值列表
     */
    private void addInParameters(StringBuilder wBuilder, Map<String, Object> parameterTarget, Parameter pa,
            List<String> paValue) {
        int length = paValue.size();
        // 构建in参数的参数序列
        wBuilder.append(getColumnSplitCharStart()).append(pa.getColumn()).append(getColumnSplitCharEnd())
                .append(" IN (");
        String parameterName = pa.getParameterName();
        for (int i = 0; i < length; i++) {
            wBuilder.append(":").append(parameterName).append(i);
            if (i < length - 1) {
                wBuilder.append(",");
            }
            parameterTarget.put("" + parameterName + i, new SqlParameterValue(Types.VARCHAR, paValue.get(i)));
        }
        wBuilder.append(")");
    }

    /**
     * 分析SQL语句中的命名参数，将参数添加到参数列表
     * 
     * @param sql             要分析的SQL语句
     * @param parameterTarget 要添加的参数目标
     * @param parameterSource 参数源
     */
    private void addPreparedParameters(String sql, Map<String, Object> parameterTarget,
            MultiValueMap<String, String> parameterSource) {
        ParsedSql ps = SqlUtils.parseSqlStatement(sql);
        List<String> parmeterNames = ps.getParameterNames();
        for (String parameter : parmeterNames) {
            List<String> parameterValues = parameterSource.get(parameter);
            if (CollectionUtils.isNotEmpty(parameterValues)) {
                parameterTarget.put(parameter, new SqlParameterValue(Types.VARCHAR, parameterValues.get(0)));
            }
        }
    }

    @Override
    public QuerySql getQuerySql(Svc svc, MultiValueMap<String, String> params, Pageable pageable) {
        return builderSql(svc, params, pageable);
    }
}

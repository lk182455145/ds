package com.leadingsoft.ds.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadingsoft.ds.converters.SvcConverter;
import com.leadingsoft.ds.dto.ColumnMetaData;
import com.leadingsoft.ds.dto.SvcDto;
import com.leadingsoft.ds.entities.DbConnection;
import com.leadingsoft.ds.entities.QSvc;
import com.leadingsoft.ds.entities.Svc;
import com.leadingsoft.ds.repositories.DbConnectionRepository;
import com.leadingsoft.ds.repositories.SvcRepository;
import com.leadingsoft.ds.services.DataSourceService;
import com.leadingsoft.ds.services.SvcService;
import com.querydsl.core.types.dsl.BooleanExpression;

/*
 * 缓存策略，当服务改变时,清除所有服务相关的缓存，以及依赖于服务信息的缓存
 */
@Service
public class SvcServiceImpl implements SvcService {

	@Autowired
	private SvcRepository svcRepository;

	@Autowired
	private SvcConverter svcConverter;

	@Autowired
	private DbConnectionRepository cnnRepository;

	@Autowired
	private DataSourceService dataSourceService;

	@Autowired
	private DbConnectionRepository cnnRepo;

	@Override
	@Transactional
	@CacheEvict(cacheNames = { "svcCache", "resultCache" }, allEntries = true)
	public Svc save(SvcDto dto) {
		Svc svc = new Svc();
		svcConverter.copyProperties(svc, dto);
		Long cnnId = dto.getConnectionId();
		DbConnection cnn = cnnRepository.getOne(cnnId);
		svc.setConnection(cnn);
		return svcRepository.save(svc);
	}

	@Override
	@Transactional
	@CacheEvict(cacheNames = { "svcCache", "resultCache" }, allEntries = true)
	public Optional<Svc> delete(Long id) {
		Optional<Svc> svc = svcRepository.findById(id);
		if (svc.isPresent()) {
			Svc svc_ = svc.get();
			svc_.getOrders();
			svc_.getParameters();
		}

		if (!Objects.isNull(svc)) {
			svcRepository.deleteById(id);
		}
		return svc;
	}

	@Override
	@Transactional
	@CacheEvict(cacheNames = { "svcCache", "resultCache" }, allEntries = true)
	public Svc update(Long id, SvcDto dto) {
		Svc svc = svcRepository.getOne(id);
		svcConverter.copyProperties(svc, dto);
		Long cnnId = dto.getConnectionId();
		DbConnection cnn = cnnRepository.getOne(cnnId);
		svc.setConnection(cnn);
		return svc;
	}

	@Override
	public Optional<Svc> get(Long id) {
		return svcRepository.findById(id);
	}

	@Override
	@Cacheable(cacheNames = "svcCache", key = "'name-'+#service", sync = true)
	public Svc findByName(String service) {
		return svcRepository.findByName(service);
	}

	@Override
	public List<ColumnMetaData> getColumnMetas(Svc svc) throws SQLException {
		String metaSql = NamedParameterUtils.parseSqlStatementIntoString(svc.getSql());
		DataSource dataSource = dataSourceService.get(svc.getConnection());
		try (Connection cnn = dataSource.getConnection();
				PreparedStatement statement = cnn.prepareStatement(metaSql)) {
			ResultSetMetaData metaData = statement.getMetaData();
			int columnCount = metaData.getColumnCount();
			List<ColumnMetaData> result = new LinkedList<ColumnMetaData>();
			for (int i = 1; i <= columnCount; i++) {
				ColumnMetaData metaData_ = new ColumnMetaData(metaData.getColumnName(i), metaData.getColumnLabel(i),
						metaData.getColumnType(i), metaData.getColumnTypeName(i));
				result.add(metaData_);
			}
			return result;
		}
	}

	@Override
	public List<ColumnMetaData> getColumnMetas(SvcDto svc) throws SQLException {
		Svc svc_ = new Svc();
		svcConverter.copyProperties(svc_, svc);
		DbConnection cnn = cnnRepo.findById(svc.getConnectionId()).get();
		svc_.setConnection(cnn);
		return getColumnMetas(svc_);
	}

	@Override
	public Page<Svc> list(Pageable pageable) {
		return svcRepository.findAll(pageable);
	}

	@Override
	public Page<Svc> list(String search, Pageable pageable) {
		if (StringUtils.isBlank(search)) {
			return list(pageable);
		} else {
			BooleanExpression exp = QSvc.svc.name.containsIgnoreCase(search)
					.or(QSvc.svc.description.containsIgnoreCase(search));
			return svcRepository.findAll(exp, pageable);
			
		}

	}

	@Override
	public Svc patch(@NotNull Long id, SvcDto dto) {
		throw new NotImplementedException("该方法未实现");
	}
}

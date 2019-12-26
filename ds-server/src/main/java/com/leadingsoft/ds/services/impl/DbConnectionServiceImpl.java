package com.leadingsoft.ds.services.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadingsoft.ds.converters.DbConnectionConverter;
import com.leadingsoft.ds.dto.DbConnectionDto;
import com.leadingsoft.ds.entities.DbConnection;
import com.leadingsoft.ds.repositories.DbConnectionRepository;
import com.leadingsoft.ds.services.DataSourceService;
import com.leadingsoft.ds.services.DbConnectionService;

/**
 * 
 * @author LiDong
 *
 */
/*
 * 数据缓存策略，当连接改变时，清楚所有的服务相关的缓存，连接缓存，还有列信息缓存
 */
@Service
public class DbConnectionServiceImpl implements DbConnectionService {

	@Autowired
	private DbConnectionRepository cnnRepository;

	@Autowired
	private DbConnectionConverter cnnConverter;

	@Autowired
	private DataSourceService dataSourceService;

	@Override
	@Transactional
	@CacheEvict(cacheNames = { "svcCache", "resultCache" }, allEntries = true)
	public DbConnection save(DbConnectionDto dto) {
		DbConnection cnn = new DbConnection();
		cnnConverter.copyProperties(cnn, dto);
		return cnnRepository.save(cnn);
	}

	@Override
	@Transactional
	@CacheEvict(cacheNames = { "svcCache", "resultCache" }, allEntries = true)
	public Optional<DbConnection> delete(Long id) {
		Optional<DbConnection> cnn = cnnRepository.findById(id);
		cnnRepository.deleteById(id);
		closeConnection(cnn.get());
		return cnn;
	}

	@Override
	@Transactional
	@CacheEvict(cacheNames = { "svcCache", "resultCache" }, allEntries = true)
	public DbConnection update(Long id, DbConnectionDto dto) {
		DbConnection cnn = cnnRepository.findById(id).get();
		closeConnection(cnn);
		cnnConverter.copyProperties(cnn, dto);
		return cnn;
	}

	@Override
	public Optional<DbConnection> get(Long id) {
		return cnnRepository.findById(id);
	}

	/**
	 * 关闭连接池中的连接
	 * 
	 * @param id
	 */
	private void closeConnection(DbConnection cnn) {
		dataSourceService.remove(cnn.getDbType().toString(), cnn.getUrl(), cnn.getUsername(), cnn.getPassword());
	}

	@Override
	public Page<DbConnection> list(Pageable pageable) {
		return cnnRepository.findAll(pageable);
	}

	@Override
	public List<DbConnection> listAll() {
		return cnnRepository.findAll();
	}

	@Override
	public DbConnection patch(@NotNull Long id, DbConnectionDto dto) {
		throw new NotImplementedException("该方法未实现");
	}

}

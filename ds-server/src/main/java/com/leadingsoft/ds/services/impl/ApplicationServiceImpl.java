package com.leadingsoft.ds.services.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadingsoft.ds.converters.ApplicationConverter;
import com.leadingsoft.ds.dto.ApplicationDto;
import com.leadingsoft.ds.entities.Application;
import com.leadingsoft.ds.repositories.ApplicationRepository;
import com.leadingsoft.ds.services.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationConverter appConverter;

	@Autowired
	private ApplicationRepository appRepository;

	@Override
	@Transactional
	@CacheEvict(cacheNames = { "appCache" }, allEntries = true)
	public Application save(ApplicationDto dto) {
		Application app = new Application();
		appConverter.copyProperties(app, dto);
		return appRepository.save(app);
	}

	@Override
	@Transactional
	@CacheEvict(cacheNames = { "appCache" }, allEntries = true)
	public Optional<Application> delete(String id) {
		Optional<Application> app = appRepository.findById(id);
		appRepository.deleteById(id);
		return app;
	}

	@Override
	@Transactional
	@CacheEvict(cacheNames = { "appCache" }, allEntries = true)
	public Application update(String id, ApplicationDto dto) {
		Application app = appRepository.findById(id).get();
		appConverter.copyProperties(app, dto);
		return app;
	}

	@Override
	public Optional<Application> get(String id) {
		return appRepository.findById(id);
	}

	@Override
	@Cacheable(cacheNames = { "appCache" }, sync = true, key = "'app#all'")
	public List<Application> listAll() {
		return appRepository.findAll();
	}

	@Override
	public Page<Application> list(Pageable pageable) {
		return appRepository.findAll(pageable);
	}

	@Override
	public Application patch(@NotNull String id, ApplicationDto dto) {
		throw new NotImplementedException("这个方法没有更新");
	}

}

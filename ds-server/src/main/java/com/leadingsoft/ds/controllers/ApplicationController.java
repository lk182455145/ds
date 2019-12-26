package com.leadingsoft.ds.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dm.common.exception.DataNotExistException;
import com.leadingsoft.ds.converters.ApplicationConverter;
import com.leadingsoft.ds.dto.ApplicationDto;
import com.leadingsoft.ds.entities.Application;
import com.leadingsoft.ds.services.ApplicationService;

@RestController
@RequestMapping("/app")
public class ApplicationController {

	@Autowired
	private ApplicationService appService;

	@Autowired
	private ApplicationConverter appConverter;

	@PostMapping
	public ApplicationDto save(@RequestBody ApplicationDto app) {
		Application app_ = appService.save(app);
		return appConverter.toDto(app_);
	}

	@PutMapping("{id}")
	public ApplicationDto update(
			@PathVariable("id") String id,
			@RequestBody ApplicationDto app) {
		Application app_ = appService.update(id, app);
		return appConverter.toDto(app_);
	}

	@GetMapping("{id}")
	public ApplicationDto get(@PathVariable("id") String id) {
		Optional<Application> app_ = appService.get(id);
		return app_.map(appConverter::toDto).orElseThrow(DataNotExistException::new);
	}

	@GetMapping
	public Page<ApplicationDto> list(
			@RequestParam("draw") Long draw,
			@PageableDefault Pageable pageable) {
			Page<Application> results = appService.list(pageable);
			return results.map(appConverter::toDto);
	}

}

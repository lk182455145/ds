package com.leadingsoft.ds.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.leadingsoft.ds.dto.ApplicationDto;
import com.leadingsoft.ds.entities.Application;

public interface ApplicationService extends CrudService<Application, ApplicationDto, String> {

	public Page<Application> list(Pageable pageable);

	public List<Application> listAll();

}

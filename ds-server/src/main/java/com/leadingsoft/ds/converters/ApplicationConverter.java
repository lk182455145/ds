package com.leadingsoft.ds.converters;

import org.springframework.stereotype.Component;

import com.dm.common.converter.Converter;
import com.leadingsoft.ds.dto.ApplicationDto;
import com.leadingsoft.ds.entities.Application;

@Component
public class ApplicationConverter implements Converter<Application, ApplicationDto> {

	@Override
	public Application copyProperties(Application model, ApplicationDto dto) {
		model.setName(dto.getName());
		model.setDescription(dto.getDescription());
		return model;
	}

	@Override
	public ApplicationDto toDto(Application model) {
		ApplicationDto dto = new ApplicationDto();
		dto.setCreateDate(model.getCreateDate());
		dto.setDescription(model.getDescription());
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setUpdateDate(model.getUpdateDate());
		return dto;
	}

}

package com.leadingsoft.ds.converters;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dm.common.converter.Converter;
import com.leadingsoft.ds.dto.SvcDto;
import com.leadingsoft.ds.entities.Svc;

@Component
public class SvcConverter implements Converter<Svc, SvcDto> {
	@Autowired
	private OrderConverter orderConverter;

	@Autowired
	private ParameterConverter parameterConverter;

	@Override
	public SvcDto toDto(Svc model) {
		if (Objects.isNull(model)) {
			return null;
		} else {
			SvcDto dto = new SvcDto();
			dto.setId(model.getId());
			dto.setName(model.getName());
			dto.setOrders(orderConverter.toDto(model.getOrders()));
			dto.setParameters(parameterConverter.toDto(model.getParameters()));
			dto.setSql(model.getSql());
			dto.setDescription(model.getDescription());
			dto.setConnectionId(model.getConnection().getId());
			return dto;
		}
	}

	@Override
	public Svc copyProperties(Svc model, SvcDto dto) {
		model.setName(dto.getName());
		model.setSql(dto.getSql());
		model.setOrders(orderConverter.toModel(dto.getOrders()));
		model.setParameters(parameterConverter.toModel(dto.getParameters()));
		model.setDescription(dto.getDescription());
		return model;
	}

//	@Override
//	public SvcDto toTableResultDto(Svc model) {
//		if (Objects.isNull(model)) {
//			return null;
//		} else {
//			SvcDto dto = new SvcDto();
//			dto.setId(model.getId());
//			dto.setName(model.getName());
//			dto.setSql(model.getSql());
//			dto.setDescription(model.getDescription());
//			dto.setConnectionId(model.getConnection().getId());
//			return dto;
//		}
//	}

}

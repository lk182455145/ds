package com.leadingsoft.ds.converters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Component;

import com.dm.common.converter.Converter;
import com.leadingsoft.ds.dto.ParameterDto;
import com.leadingsoft.ds.entities.Parameter;

@Component
public class ParameterConverter implements Converter<Parameter, ParameterDto> {

	@Override
	public ParameterDto toDto(Parameter model) {
		ParameterDto dto = new ParameterDto();
		dto.setColumn(model.getColumn());
		dto.setOperator(model.getOperator());
		dto.setDescription(model.getDescription());
		dto.setParameterName(model.getParameterName());
		return dto;
	}

	@Override
	public Parameter copyProperties(Parameter model, ParameterDto dto) {
		model.setColumn(dto.getColumn());
		model.setOperator(dto.getOperator());
		model.setParameterName(dto.getParameterName());
		model.setDescription(dto.getDescription());
		return model;
	}

	private Parameter toModel(ParameterDto _parameter) {
		Parameter parameter = new Parameter();
		copyProperties(parameter, _parameter);
		return parameter;
	}

	public List<Parameter> toModel(Iterable<ParameterDto> _parameters) {
		if (IterableUtils.isEmpty(_parameters)) {
			return Collections.emptyList();
		} else {
			List<Parameter> parameters_ = new ArrayList<Parameter>();
			for (ParameterDto _parameter : _parameters) {
				parameters_.add(toModel(_parameter));
			}
			return parameters_;
		}
	}

}

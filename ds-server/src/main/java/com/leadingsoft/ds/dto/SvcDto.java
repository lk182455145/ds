package com.leadingsoft.ds.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SvcDto implements Serializable {
	private static final long serialVersionUID = -6098284924857636923L;
	private Long connectionId;
	private Long id;
	private String sql;
	private String name;
	private String description;
	private List<ParameterDto> parameters;
	private List<OrderDto> orders;
}

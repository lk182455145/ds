package com.leadingsoft.ds.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ParameterDto implements Serializable {
	private static final long serialVersionUID = -5064655623756192629L;

	private String column;

	private String operator;

	private String parameterName;
}

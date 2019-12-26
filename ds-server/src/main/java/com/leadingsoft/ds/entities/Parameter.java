package com.leadingsoft.ds.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Parameter implements Serializable {

	private static final long serialVersionUID = 2203811797498404695L;

	@Column(name = "column_")
	private String column;

	private String operator;

	private String parameterName;
}

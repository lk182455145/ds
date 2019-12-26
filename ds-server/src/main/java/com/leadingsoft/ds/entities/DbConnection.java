package com.leadingsoft.ds.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.dm.common.entity.AbstractEntity;
import com.leadingsoft.ds.dto.DbConnectionDto.DBType;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "connection_")
@Getter
@Setter
public class DbConnection extends AbstractEntity {

	private static final long serialVersionUID = 8353459514057177389L;

	@Enumerated(EnumType.STRING)
	private DBType dbType;

	@Column(unique = true, nullable = false)
	private String name;

	private String url;
	private String username;
	private String password;

}

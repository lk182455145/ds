package com.leadingsoft.ds.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.dm.common.entity.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "service_")
@Getter
@Setter
public class Svc extends AbstractEntity {
	private static final long serialVersionUID = 744996370403248087L;

	@ManyToOne
	private DbConnection connection;

	@Column(unique = true, nullable = false)
	private String name;

	@Lob
	@Column(name = "sql_")
	private String sql;

	@ElementCollection(fetch = FetchType.EAGER)
	@Embedded
	@Fetch(FetchMode.SUBSELECT)
	private List<Parameter> parameters;

	@ElementCollection(fetch = FetchType.EAGER)
	@Embedded
	@Fetch(FetchMode.SUBSELECT)
	private List<Order> orders;
	
	@Lob
	private String description;
}

package com.leadingsoft.ds.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "application_")
@EntityListeners(AuditingEntityListener.class)
public class Application implements Serializable {

	private static final long serialVersionUID = -3434258837235304253L;

	@Column(length = 32)
	@Id
	private String id;

	@Column(unique = true, nullable = false)
	private String name;

	private String description;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	public Application() {
		String id = UUID.randomUUID().toString();
		this.id = StringUtils.replace(id, "-", "");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Application other = (Application) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

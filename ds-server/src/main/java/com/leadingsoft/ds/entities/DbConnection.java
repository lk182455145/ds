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
    @Column(name = "db_type_")
    private DBType dbType;

    @Column(name = "name_", unique = true, nullable = false, length = 100)
    private String name;

    @Column(name = "url_", length = 400)
    private String url;

    @Column(name = "username_", length = 100)
    private String username;

    @Column(name = "password_", length = 100)
    private String password;

}

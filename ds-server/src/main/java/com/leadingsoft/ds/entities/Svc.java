package com.leadingsoft.ds.entities;

import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.UniqueConstraint;

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

    @Column(name = "name_", unique = true, nullable = false)
    private String name;

    @Lob
    @Column(name = "sql_")
    private String sql;

    @ElementCollection(fetch = FetchType.EAGER)
    @Embedded
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "service_parameters_", joinColumns = {
            @JoinColumn(name = "service_id_")
    }, uniqueConstraints = {
            @UniqueConstraint(columnNames = { "service_id_", "parameter_name_" })
    })
    private List<Parameter> parameters;

    @ElementCollection
    @Embedded
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "service_required_parameters_", joinColumns = {
            @JoinColumn(name = "service_id_")
    }, uniqueConstraints = {
            @UniqueConstraint(columnNames = { "service_id_", "parameter_name_" })
    })
    private List<Parameter> requiredParameters;

    @ElementCollection(fetch = FetchType.EAGER)
    @Embedded
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "service_orders_", joinColumns = {
            @JoinColumn(name = "service_id_")
    }, uniqueConstraints = {
            @UniqueConstraint(columnNames = { "service_id_", "column_" })
    })
    private List<Order> orders;

    /**
     * 包含的列的输出字段的说明
     */
    @ElementCollection
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "service_columns_", joinColumns = {
            @JoinColumn(name = "service_id_")
    })
    @Column(name = "description_")
    @MapKeyColumn(name = "column_name_")
    private Map<String, String> columns;

    @Lob
    @Column(name = "description_")
    private String description;

}

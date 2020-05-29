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

    @Column(name = "column_", length = 100)
    private String column;

    @Column(name = "description_", length = 200)
    private String description;

    @Column(name = "operator_", length = 10)
    private String operator;

    @Column(name = "parameter_name_", length = 200)
    private String parameterName;

}

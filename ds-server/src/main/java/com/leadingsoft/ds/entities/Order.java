package com.leadingsoft.ds.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.leadingsoft.ds.dto.OrderDto.Direction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Order implements Serializable {

    private static final long serialVersionUID = 5290684778590238497L;

    @Column(name = "column_", length = 200)
    private String column;

    @Column(name = "direction_", length = 10)
    @Enumerated(EnumType.STRING)
    private Direction direction;
}

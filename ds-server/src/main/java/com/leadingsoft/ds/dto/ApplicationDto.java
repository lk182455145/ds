package com.leadingsoft.ds.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class ApplicationDto implements Serializable {
    private static final long serialVersionUID = -30316509955568280L;

    private String id;

    private String name;

    private String description;

    private ZonedDateTime createDate;

    private ZonedDateTime updateDate;
}

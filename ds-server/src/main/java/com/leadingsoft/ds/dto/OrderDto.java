package com.leadingsoft.ds.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrderDto implements Serializable {
	private static final long serialVersionUID = 3492121281643509383L;

	/**
	 * 排序方式
	 * 
	 * @author LiDong
	 *
	 */
	public enum Direction {

		ASC, DESC

	}

	private String column;
	private Direction direction;

}

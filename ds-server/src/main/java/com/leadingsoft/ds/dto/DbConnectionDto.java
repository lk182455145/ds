package com.leadingsoft.ds.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DbConnectionDto implements Serializable {
	
	/**
	 * 定义数据连接类型为
	 */
	public enum DBType {
		SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver"),

		MYSQL("com.mysql.cj.jdbc.Driver"),

		MARIADB("org.mariadb.jdbc.Driver");

		private String driver;

		private DBType(String driver) {
			this.driver = driver;
		}

		public String getDriver() {
			return driver;
		}
	}
	
	private static final long serialVersionUID = 7238048377690553252L;
	private Long id;
	private DBType dbType;
	private String name;
	private String url;
	private String username;
	private String password;

}

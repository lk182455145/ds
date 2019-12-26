package com.leadingsoft.ds.services;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.leadingsoft.ds.dto.DbConnectionDto;
import com.leadingsoft.ds.entities.DbConnection;

/**
 * 连接池管理服务，用于管理数据库连接池信息<br />
 * 
 * 连接池以 driver,url,username,password为key进行缓存
 * 
 * @author LiDong
 *
 */
public interface DataSourceService {

	/**
	 * 根据指定参数获取连接池
	 * 
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	public DataSource get(String driver, String url, String username, String password);

	/**
	 * 根据指定参数关闭连接池
	 * 
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 */
	public void remove(String driver, String url, String username, String password);

	/**
	 * 检测连接状态
	 * 
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean checkState(String driver, String url, String username, String password) throws SQLException;

	/**
	 * 获取连接
	 * 
	 * @param connection
	 * @return
	 */
	public DataSource get(DbConnection connection);

	/**
	 * 获取连接
	 * 
	 * @param connection
	 * @return
	 */
	public DataSource get(DbConnectionDto connection);

	/**
	 * 移除连接
	 * 
	 * @param connection
	 */
	public void remove(DbConnection connection);

	public void remove(DbConnectionDto connection);

	public boolean checkState(DbConnection cnn) throws SQLException;

	public boolean checkState(DbConnectionDto cnn) throws SQLException;
}

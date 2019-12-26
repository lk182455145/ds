package com.leadingsoft.ds.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.leadingsoft.ds.dto.ColumnMetaData;
import com.leadingsoft.ds.dto.SvcDto;
import com.leadingsoft.ds.entities.Svc;

/**
 * 服务配置服务
 * 
 * @author LiDong
 *
 */
public interface SvcService extends CrudService<Svc, SvcDto, Long> {

	/**
	 * 根据服务名称获取服务
	 * 
	 * @param service
	 * @return
	 */
	public Svc findByName(String service);

	/**
	 * 获取服务的列信息
	 * 
	 * @param svc
	 * @return
	 * @throws SQLException
	 */
	public List<ColumnMetaData> getColumnMetas(Svc svc) throws SQLException;

	/**
	 * 获取服务的列信息
	 * 
	 * @param svc
	 * @return
	 * @throws SQLException
	 */
	public List<ColumnMetaData> getColumnMetas(SvcDto svc) throws SQLException;

	/**
	 * 分页获取服务信息
	 * 
	 * @param pageable
	 * @return
	 */
	public Page<Svc> list(Pageable pageable);

	/**
	 * 根据搜索关键字分页查询
	 * 
	 * @param search
	 * @param pageable
	 * @return
	 */
	public Page<Svc> list(String search, Pageable pageable);

}

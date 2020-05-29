package com.leadingsoft.ds.services;

import java.sql.SQLException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

import com.leadingsoft.ds.dto.SqlMetaData;
import com.leadingsoft.ds.entities.DbConnection;

public interface CrudService<Model, Dto, PK> {
	/**
	 * 保存一条信息
	 * 
	 * @param dto
	 * @return
	 */
	public Model save(@NotNull Dto dto);

	/**
	 * 删除一跳信息
	 * 
	 * @param id
	 * @return
	 */
	public void delete(@NotNull PK id);

	/**
	 * 修改一条信息
	 * 
	 * @param dto
	 * @return
	 */
	public Model update(@NotNull PK id, Dto dto) throws EntityNotFoundException;

	/**
	 * 更新一条信息
	 * 
	 * @param id
	 * @param dto
	 * @return
	 */
	public Model patch(@NotNull PK id, Dto dto);

	/**
	 * 获取一条信息
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Model> get(@NotNull PK id);

}

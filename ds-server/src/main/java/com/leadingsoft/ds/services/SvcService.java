package com.leadingsoft.ds.services;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.leadingsoft.ds.dto.SqlMetaData;
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
    public Optional<Svc> findByName(String service);

    /**
     * 获取服务的元数据信息
     * 
     * @param svc
     * @return
     * @throws SQLException
     */
    public SqlMetaData getMeta(SvcDto svc) throws SQLException;

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

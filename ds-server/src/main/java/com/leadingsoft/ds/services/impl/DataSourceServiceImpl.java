package com.leadingsoft.ds.services.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import com.leadingsoft.ds.dto.DbConnectionDto;
import com.leadingsoft.ds.entities.DbConnection;
import com.leadingsoft.ds.services.DataSourceService;
import com.zaxxer.hikari.HikariDataSource;

import lombok.Data;

/**
 * 连接池服务实现<br/>
 * 使用一个 {@link ConcurrentHashMap}保存连接池信息,<br />
 * 
 * @author LiDong
 *
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {

    private final Map<ConnectionInfo, HikariDataSource> sourceMap;

    private final Object lock = new Object();

    @Data
    static class ConnectionInfo implements Serializable {
        private static final long serialVersionUID = -1881949477466309614L;
        private String driver;
        private String url;
        private String username;
        private String password;

        public ConnectionInfo(String driver, String url, String username, String password) {
            super();
            this.driver = driver;
            this.url = url;
            this.username = username;
            this.password = password;
        }

    }

    public DataSourceServiceImpl() {
        sourceMap = new ConcurrentHashMap<>();
    }

    /**
     * 获取连接信息
     */
    @Override
    public DataSource get(String driver, String url, String username, String password) {
        ConnectionInfo info = new ConnectionInfo(driver, url, username, password);
        HikariDataSource dataSource = sourceMap.get(info);
        if (dataSource == null) {
            synchronized (lock) {
                dataSource = sourceMap.get(info);
                if (dataSource == null) {
                    dataSource = new HikariDataSource();
                    dataSource.setDriverClassName(driver);
                    dataSource.setJdbcUrl(url);
                    dataSource.setMaximumPoolSize(50);
                    dataSource.setUsername(username);
                    dataSource.setPassword(password);
                    sourceMap.put(info, dataSource);
                }
            }
        }
        return dataSource;
    }

    @Override
    public void remove(String driver, String url, String username, String password) {
        ConnectionInfo info = new ConnectionInfo(driver, url, username, password);
        HikariDataSource source = sourceMap.remove(info);
        if (!Objects.isNull(source)) {
            source.close();
        }
    }

    @Override
    public DataSource get(DbConnection connection) {
        return get(connection.getDbType().getDriver(), connection.getUrl(), connection.getUsername(),
                connection.getPassword());
    }

    @Override
    public DataSource get(DbConnectionDto connection) {
        return get(connection.getDbType().getDriver(), connection.getUrl(), connection.getUsername(),
                connection.getPassword());
    }

    @Override
    public void remove(DbConnection connection) {
        remove(connection.getDbType().getDriver(),
                connection.getUrl(),
                connection.getUsername(),
                connection.getPassword());
    }

    @Override
    public void remove(DbConnectionDto connection) {
        remove(connection.getDbType().getDriver(),
                connection.getUrl(),
                connection.getUsername(),
                connection.getPassword());
    }

    @Override
    public boolean checkState(String driver, String url, String username, String password) {
        HikariDataSource source = (HikariDataSource) get(driver, url, username, password);
        try (Connection cnn_ = source.getConnection()) {
            cnn_.createStatement();
            return true;
        } catch (SQLException e) {
            remove(driver, url, username, password);
            return false;
        }

    }

    @Override
    public boolean checkState(DbConnection cnn) throws SQLException {
        return checkState(cnn.getDbType().getDriver(),
                cnn.getUrl(),
                cnn.getUsername(),
                cnn.getPassword());
    }

    @Override
    public boolean checkState(DbConnectionDto cnn) throws SQLException {
        return checkState(cnn.getDbType().getDriver(),
                cnn.getUrl(),
                cnn.getUsername(),
                cnn.getPassword());
    }

}

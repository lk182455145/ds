package com.leadingsoft.ds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leadingsoft.ds.entities.DbConnection;

public interface DbConnectionRepository extends JpaRepository<DbConnection, Long> {

}

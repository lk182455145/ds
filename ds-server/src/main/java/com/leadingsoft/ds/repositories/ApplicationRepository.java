package com.leadingsoft.ds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leadingsoft.ds.entities.Application;

public interface ApplicationRepository extends JpaRepository<Application, String> {

}

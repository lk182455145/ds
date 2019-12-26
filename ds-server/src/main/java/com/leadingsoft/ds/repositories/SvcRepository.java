package com.leadingsoft.ds.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.leadingsoft.ds.entities.Svc;

public interface SvcRepository extends JpaRepository<Svc, Long>, QuerydslPredicateExecutor<Svc> {
	public Svc findByName(String name);

	public Page<Svc> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String search, Pageable pageable);
}

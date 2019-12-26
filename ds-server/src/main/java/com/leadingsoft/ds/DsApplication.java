package com.leadingsoft.ds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
public class DsApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DsApplication.class, args);
	}
}

package com.sri.springrevise.configuration.db;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.sri.springrevise.repository.sql")
@EntityScan(basePackages = "com.sri.springrevise.model.sql")
public class JpaConfig { }
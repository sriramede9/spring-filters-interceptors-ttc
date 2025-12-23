package com.sri.springrevise.configuration.db;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.sri.springrevise.repository.mongo")
public class MongoDbConfig { }
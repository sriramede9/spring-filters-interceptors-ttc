package com.sri.springrevise.repository;

import com.sri.springrevise.model.Bus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusRepository extends MongoRepository<Bus, String>, BusRepositoryCustom {

}

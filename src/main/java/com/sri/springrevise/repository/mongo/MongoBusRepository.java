package com.sri.springrevise.repository.mongo;

import com.sri.springrevise.model.mongo.MongoBus;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MongoBusRepository extends MongoRepository<MongoBus, String>, BusRepositoryCustom {

}

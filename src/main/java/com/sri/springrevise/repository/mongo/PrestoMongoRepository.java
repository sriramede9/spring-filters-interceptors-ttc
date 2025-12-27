package com.sri.springrevise.repository.mongo;

import com.sri.springrevise.model.mongo.PrestoCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestoMongoRepository extends MongoRepository<PrestoCard, String> {
}

package com.sri.springrevise.service.mongo;

import com.sri.springrevise.model.mongo.PrestoCard;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PrestoService {
    private MongoTemplate mongoTemplate;

    public PrestoService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Double addFundsAtomic(String cardId, Double amount) {
        Query query = new Query(Criteria.where("cardId").is(cardId));
        Update update = new Update()
                .inc("balance", amount)   // 1. Update the money
                .inc("version", 1)       // 2. Update the counter
                .set("lastModified", LocalDateTime.now()); // 3. Set the timestamp
        // findAndModify updates the doc in one step in the DB
        PrestoCard updatedCard = mongoTemplate.findAndModify(
                query, update, FindAndModifyOptions.options().returnNew(true), PrestoCard.class);

        return updatedCard.getBalance();
    }

}

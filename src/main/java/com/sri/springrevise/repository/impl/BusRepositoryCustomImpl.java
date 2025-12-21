package com.sri.springrevise.repository.impl;

import com.sri.springrevise.model.Bus;
import com.sri.springrevise.model.Passenger;
import com.sri.springrevise.repository.BusRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

@RequiredArgsConstructor
public class BusRepositoryCustomImpl implements BusRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public Bus addPassengerAtomic(String busId, Passenger passenger) {
        Query query = new Query(Criteria.where("id").is(busId));
        Update update = new Update().push("passengers", passenger);

        // This returns the UPDATED document in one trip to the DB
        return mongoTemplate.findAndModify(
                query,
                update,
                new FindAndModifyOptions().returnNew(true),
                Bus.class
        );
    }

    @Override
    public List<Passenger> findPassengersPaginated(String busId, int skip, int limit) {
        // We use Projection to only get the passengers array and slice it
        Query query = new Query(Criteria.where("id").is(busId));
        query.fields().include("passengers").slice("passengers", skip, limit);

        Bus bus = mongoTemplate.findOne(query, Bus.class);
        return (bus != null) ? bus.getPassengers() : List.of();
    }
}

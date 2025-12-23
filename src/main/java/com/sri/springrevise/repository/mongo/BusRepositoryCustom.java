package com.sri.springrevise.repository.mongo;

import com.sri.springrevise.model.mongo.MongoBus;
import com.sri.springrevise.model.mongo.Passenger;

import java.util.List;

public interface BusRepositoryCustom {
    MongoBus addPassengerAtomic(String busId, Passenger passenger);
    List<Passenger> findPassengersPaginated(String busId, int skip, int limit);
}
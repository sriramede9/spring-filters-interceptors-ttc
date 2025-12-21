package com.sri.springrevise.repository;

import com.sri.springrevise.model.Bus;
import com.sri.springrevise.model.Passenger;

import java.util.List;

public interface BusRepositoryCustom {
    Bus addPassengerAtomic(String busId, Passenger passenger);
    List<Passenger> findPassengersPaginated(String busId, int skip, int limit);
}
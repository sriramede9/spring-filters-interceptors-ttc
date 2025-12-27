package com.sri.springrevise.service.mongo;

import com.sri.springrevise.model.mongo.MongoBus;
import com.sri.springrevise.model.sql.Bus;
import com.sri.springrevise.repository.mongo.MongoBusRepository;
import com.sri.springrevise.repository.sql.SqlBusRepository;
import org.springframework.stereotype.Service;

@Service
public class TransitService {

    private final SqlBusRepository sqlBusRepository;   // JPA/Postgres
    private final MongoBusRepository mongoBusRepository; // MongoDB

    // Constructor Injection (Best Practice)
    public TransitService(SqlBusRepository sqlBusRepository, MongoBusRepository mongoBusRepository) {
        this.sqlBusRepository = sqlBusRepository;
        this.mongoBusRepository = mongoBusRepository;
    }

    public void saveBusData(Bus sqlBus, MongoBus mongoDoc) {
        sqlBusRepository.save(sqlBus);       // Goes to Postgres
        mongoBusRepository.save(mongoDoc);   // Goes to MongoDB
    }
}
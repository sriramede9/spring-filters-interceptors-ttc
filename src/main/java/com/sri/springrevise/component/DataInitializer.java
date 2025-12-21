package com.sri.springrevise.component;

import com.sri.springrevise.model.Bus;
import com.sri.springrevise.model.Passenger;
import com.sri.springrevise.repository.BusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {

    private final BusRepository busRepository;

    public DataInitializer(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("🚌 TTC System Ignition: Seeding Initial Bus Data...");
        
        busRepository.deleteAll(); // Start fresh for local dev

        // Create sample passengers
        Passenger p1 = Passenger.builder()
                .name("Toronto Commuter")
                .ticketId("TKT-12345")
                .boardingStop("Union Station")
                .build();

        // Bus 1: Queen St Route
        Bus bus1 = Bus.builder()
                .id("501")
                .routeName("501 Queen")
                .driverName("John Doe")
                .internalDepotCode("CODE-508")
                .lastServiceDate(LocalDateTime.now())
                .active(true)
                .passengers(new ArrayList<>(List.of(p1))) // Embedded passenger
                .build();

        // Bus 2: Yonge St Route (Empty)
        Bus bus2 = Bus.builder()
                .id("320")
                .routeName("320 Yonge")
                .driverName("Jane Smith")
                .internalDepotCode("CODE-320")
                .lastServiceDate(LocalDateTime.now())
                .active(true)
                .passengers(new ArrayList<>()) // Initialize empty list
                .build();
        
        busRepository.saveAll(List.of(bus1, bus2));
        
        log.info("✅ Seeding complete. 2 buses (and 1 passenger) are now in the depot.");
    }
}
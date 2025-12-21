package com.sri.springrevise.component;

import com.sri.springrevise.model.Bus;
import com.sri.springrevise.repository.BusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
        
        busRepository.deleteAll(); // Start fresh

        Bus bus1 = new Bus("501", "John Doe", "Queen St", true, LocalDateTime.now(),"CODE-508");
        Bus bus2 = new Bus("320", "Jane Smith", "Yonge St", true,LocalDateTime.now(),"CODE-320");
        
        busRepository.saveAll(List.of(bus1, bus2));
        
        log.info("✅ Seeding complete. 2 buses are now in the depot.");
    }
}
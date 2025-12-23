package com.sri.springrevise.service.sql;

import com.sri.springrevise.dto.response.BusDTO;
import com.sri.springrevise.model.sql.Bus;
import com.sri.springrevise.model.sql.Engine;
import com.sri.springrevise.repository.sql.SqlBusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
public class BusSqlService {

    private final SqlBusRepository sqlBusRepository;

    public BusSqlService(SqlBusRepository sqlBusRepository) {
        this.sqlBusRepository = sqlBusRepository;
    }

    @Transactional
    public Bus createBusWithEngine(String serial) {
        Bus bus = new Bus();
        Engine engine = new Engine();

        engine.setSerialNumber(serial);

        // Wire them together
        bus.setEngine(engine);

        // Because of CascadeType.ALL, saving the bus saves the engine!
        return sqlBusRepository.save(bus);
    }

    public Stream<BusDTO> getAllBuses() {
        List<Bus> busList = sqlBusRepository.findAllWithEngines();
        System.out.println("check with Entity Graph");
        List<Bus> busList2 = sqlBusRepository.findAll();
        return convertDto(busList);
    }

    private Stream<BusDTO> convertDto(List<Bus> busList) {
        System.out.println("we are checking lazy initialization");
        return busList.stream().map(bus -> {
            BusDTO busDTO = new BusDTO();
            busDTO.setId(bus.getId().toString());
            busDTO.setDriverName(bus.getEngine().getSerialNumber());
            return busDTO;
        });
    }
}
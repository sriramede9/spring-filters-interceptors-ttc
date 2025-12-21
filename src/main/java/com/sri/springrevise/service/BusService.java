package com.sri.springrevise.service;

import com.sri.springrevise.dto.request.BusCreateRequest;
import com.sri.springrevise.model.Bus;
import com.sri.springrevise.repository.BusRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BusService {
    private final BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public Optional<Bus> getBusyByBusId(String busId) {
        return busRepository.findById(busId);
    }

    public Bus saveBus(BusCreateRequest busCreateRequest) {
        Bus bus = createBusFromBusCreateRequest(busCreateRequest);
        return busRepository.save(bus);
    }

    private Bus createBusFromBusCreateRequest(BusCreateRequest busCreateRequest) {
        return Bus.builder()
                .lastServiceDate(busCreateRequest.getLastServiceDate())
                .driverName(busCreateRequest.getDriverName())
                .active(busCreateRequest.getActive())
                .internalDepotCode(busCreateRequest.getInternalDepotCode())
                .routeName(busCreateRequest.getRouteName())
                .build();
    }

}

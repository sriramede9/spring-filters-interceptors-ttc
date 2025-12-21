package com.sri.springrevise.service;

import com.sri.springrevise.dto.request.BusCreateRequest;
import com.sri.springrevise.dto.request.PassengerRequest;
import com.sri.springrevise.dto.response.BusDTO;
import com.sri.springrevise.model.Bus;
import com.sri.springrevise.model.Passenger;
import com.sri.springrevise.repository.BusRepository;
import jakarta.validation.Valid;
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

    public Bus addPassengerToBus(String busId, @Valid PassengerRequest request) {
        // 1. Fetch the Bus
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        // 2. Convert DTO to Model (Mapping)
        Passenger passenger = Passenger.builder()
                .name(request.getName())
                .ticketId(request.getTicketId())
                .boardingStop(request.getBoardingStop())
                .build();

        // 3. Update the list inside the Bus object
        bus.getPassengers().add(passenger);

        // 4. Save the whole Bus document back to Mongo
        Bus updatedBus = busRepository.save(bus);

        return updatedBus;
    }
}

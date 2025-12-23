package com.sri.springrevise.service;

import com.sri.springrevise.dto.request.BusCreateRequest;
import com.sri.springrevise.dto.request.PassengerRequest;
import com.sri.springrevise.exceptions.BusNotFoundException;
import com.sri.springrevise.model.mongo.MongoBus;
import com.sri.springrevise.model.mongo.Passenger;
import com.sri.springrevise.repository.mongo.MongoBusRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {
    @Qualifier()
    private final MongoBusRepository busRepository;

    public BusService(MongoBusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public Optional<MongoBus> getBusyByBusId(String busId) {
        return busRepository.findById(busId);
    }

    public MongoBus saveBus(BusCreateRequest busCreateRequest) {
        MongoBus bus = createBusFromBusCreateRequest(busCreateRequest);
        return busRepository.save(bus);
    }

    private MongoBus createBusFromBusCreateRequest(BusCreateRequest busCreateRequest) {
        return MongoBus.builder()
                .lastServiceDate(busCreateRequest.getLastServiceDate())
                .driverName(busCreateRequest.getDriverName())
                .active(busCreateRequest.getActive())
                .internalDepotCode(busCreateRequest.getInternalDepotCode())
                .routeName(busCreateRequest.getRouteName())
                .build();
    }

    public MongoBus addPassengerToBus(String busId, @Valid PassengerRequest request) {
        // 1. Create the Passenger object
        Passenger newPassenger = getPassenger(request);

        MongoBus updatedBus = busRepository.addPassengerAtomic(busId, newPassenger);

        if (updatedBus == null) {
            throw new BusNotFoundException("Bus not found: " + busId);
        }

        return updatedBus;
    }

    private static Passenger getPassenger(PassengerRequest request) {
        Passenger newPassenger = Passenger.builder()
                .name(request.getName())
                .ticketId(request.getTicketId())
                .boardingStop(request.getBoardingStop())
                .build();
        return newPassenger;
    }

    public Slice<Passenger> getPassengers(String busId, Pageable pageable) {
        int skip = (int) pageable.getOffset();
        int limit = pageable.getPageSize();
        List<Passenger> passengersPaginated = busRepository.findPassengersPaginated(busId, skip, limit+1);
        // 2. Just a pointer check, no heavy lifting here
        boolean hasNext = passengersPaginated.size() > limit;

        // 3. Create the slice. If hasNext is true, we just ignore the last element.
        List<Passenger> content = hasNext
                ? passengersPaginated.subList(0, limit)
                : passengersPaginated;

        return new SliceImpl<>(content, pageable, hasNext);

    }
}

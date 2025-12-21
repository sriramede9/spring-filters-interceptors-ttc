package com.sri.springrevise.service;

import com.sri.springrevise.dto.request.BusCreateRequest;
import com.sri.springrevise.dto.request.PassengerRequest;
import com.sri.springrevise.dto.response.BusDTO;
import com.sri.springrevise.exceptions.BusNotFoundException;
import com.sri.springrevise.model.Bus;
import com.sri.springrevise.model.Passenger;
import com.sri.springrevise.repository.BusRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
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
        // 1. Create the Passenger object
        Passenger newPassenger = getPassenger(request);

        Bus updatedBus = busRepository.addPassengerAtomic(busId, newPassenger);

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

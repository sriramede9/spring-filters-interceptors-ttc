package com.sri.springrevise.controller;

import com.sri.springrevise.dto.request.BusCreateRequest;
import com.sri.springrevise.dto.request.PassengerRequest;
import com.sri.springrevise.dto.response.BusDTO;
import com.sri.springrevise.model.Bus;
import com.sri.springrevise.service.BusService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ttc/bus")
@Validated
public class BusController {
    private final BusService busService;

    public BusController(BusService busService){
        this.busService = busService;
    }
    @GetMapping("/busDetails/{busId}")
    public ResponseEntity<BusDTO> getBusDetails(@PathVariable @NotBlank(message = "Bus ID cannot be empty") @Size(min = 3, max = 24, message = "Invalid MongoDB ID length") String busId){
        return ResponseEntity.of(busService.getBusyByBusId(busId).map(this::convertToDTO));
    }
    @PostMapping("/")
    public ResponseEntity<BusDTO> createBus(@Validated @RequestBody BusCreateRequest busCreateRequest){
        Bus bus = busService.saveBus(busCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(bus));
    }

    @PostMapping("/{busId}/passengers")
    public ResponseEntity<BusDTO> addPassenger(
            @PathVariable String busId,
            @Valid @RequestBody PassengerRequest request) {

        return ResponseEntity.ok(convertToDTO(busService.addPassengerToBus(busId, request)));
    }


    public BusDTO convertToDTO(Bus entity) {
        BusDTO dto = new BusDTO();
        dto.setId(entity.getId());
        dto.setRouteName(entity.getRouteName());
        dto.setDriverName(entity.getDriverName());
        dto.setActive(entity.getActive());
        return dto;
    }
}

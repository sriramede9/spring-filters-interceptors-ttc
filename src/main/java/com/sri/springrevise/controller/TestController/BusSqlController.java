package com.sri.springrevise.controller.TestController;

import com.sri.springrevise.dto.request.sql.BusSqlRequest;
import com.sri.springrevise.dto.response.BusDTO;
import com.sri.springrevise.model.sql.Bus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sql/buses")
public class BusSqlController {

    private final com.sri.springrevise.service.sql.BusSqlService busSqlService;

    public BusSqlController(com.sri.springrevise.service.sql.BusSqlService busSqlService) {
        this.busSqlService = busSqlService;
    }

    @PostMapping
    public Bus saveBus(@RequestBody BusSqlRequest request) {
        return busSqlService.createBusWithEngine(request.getEngineSerialNumber());
    }

    @GetMapping
    public List<BusDTO> getAll() {
        return busSqlService.getAllBuses().toList();
    }
}
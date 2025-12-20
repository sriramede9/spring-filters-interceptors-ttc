package com.sri.springrevise.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ttc")
public class TorontoTransitController {

    @GetMapping("/destination/union-station")
    public String reachUnionStation() {
        // This is the "Bus Ride" logic
        System.out.println("Controller: Passenger has reached Union Station!");
        return "Welcome to Union Station! Safe travels in the 6ix. 🍁";
    }
}
package com.sri.springrevise.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ttc")
public class TorontoTransitController {

    @GetMapping("/destination/union-station")
    public String reachUnionStation(@RequestAttribute("greeting") String greeting) {
        // The Interceptor already decided the language!
        return greeting;
    }
}
package com.sri.springrevise.controller;

import com.sri.springrevise.service.PrestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/mongo/presto")
public class PrestoController {

    @Autowired
    private PrestoService prestoService;

    @PutMapping("/add/funds/{prestoId}/{funds}")
    public Double addFundsToPresto(@PathVariable String prestoId, @PathVariable Double funds) {
        return prestoService.addFundsAtomic(prestoId, funds);
    }
}

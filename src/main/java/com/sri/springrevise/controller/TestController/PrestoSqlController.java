package com.sri.springrevise.controller.TestController;

import com.sri.springrevise.model.sql.PrestoCard;
import com.sri.springrevise.service.sql.PrestoSqlService;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/presto")
public class PrestoSqlController {
    private final PrestoSqlService prestoSqlService;

    public PrestoSqlController(PrestoSqlService prestoSqlService) {
        this.prestoSqlService = prestoSqlService;
    }

    @PutMapping("/add/funds/{prestoId}/{funds}")
    public Double addPrestoAmount(@PathVariable String prestoId, @PathVariable Double funds) {

        return prestoSqlService.addFunds(prestoId, funds);

    }
}

package com.sri.springrevise.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String name;
    private String ticketId;
    private String boardingStop;
    private Double balance;
}
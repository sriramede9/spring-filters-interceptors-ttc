package com.sri.springrevise.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "buses")
@AllArgsConstructor
@Builder
public class Bus {
    @Id
    private String id;
    private String driverName;
    private String routeName;
    private Boolean active;
    
    private LocalDateTime lastServiceDate;
    private String internalDepotCode; 
}
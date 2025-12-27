package com.sri.springrevise.model.mongo;

import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prestoCards")
@Data
public class PrestoCard {
    @Id
    private String cardId; // e.g., "PRESTO-12345"
    
    private Double balance;
    
    @Version
    private Integer version; // For non-financial updates
}
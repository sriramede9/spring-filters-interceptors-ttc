package com.sri.springrevise.model.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Table(name = "presto_cards")
@Data
public class PrestoCard {
    @Id
    private String cardId; // e.g., "PRESTO-12345"

    private Double balance;

    @Version
    private Integer version; // For non-financial updates
}
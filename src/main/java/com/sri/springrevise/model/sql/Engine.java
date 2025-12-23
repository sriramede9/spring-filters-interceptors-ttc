package com.sri.springrevise.model.sql;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "engines")
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String serialNumber;

    @OneToOne(mappedBy = "engine") // Bi-directional
    private Bus bus;
}
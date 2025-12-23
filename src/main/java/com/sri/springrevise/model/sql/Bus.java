package com.sri.springrevise.model.sql;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "buses")
@Data
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id", referencedColumnName = "id", unique = true)
    private Engine engine;
}


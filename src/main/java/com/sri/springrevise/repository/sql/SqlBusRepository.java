package com.sri.springrevise.repository.sql;

import com.sri.springrevise.model.sql.Bus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlBusRepository extends JpaRepository<Bus, String> {
    @Query("SELECT b FROM Bus b LEFT JOIN FETCH b.engine")
    List<Bus> findAllWithEngines();

    @EntityGraph(attributePaths = "engine")
    List<Bus> findAll();
}

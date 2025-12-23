package com.sri.springrevise.repository.sql;

import com.sri.springrevise.model.sql.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlBusRepository extends JpaRepository<Bus, String> {
}

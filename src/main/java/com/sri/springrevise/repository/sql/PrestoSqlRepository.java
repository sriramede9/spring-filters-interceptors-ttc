package com.sri.springrevise.repository.sql;

import com.sri.springrevise.model.sql.PrestoCard;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrestoSqlRepository extends JpaRepository<PrestoCard, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM PrestoCard c WHERE c.cardId = :cardId")
    Optional<PrestoCard> findByIdForUpdate(String cardId);
}
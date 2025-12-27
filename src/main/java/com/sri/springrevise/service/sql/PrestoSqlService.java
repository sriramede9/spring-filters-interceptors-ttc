package com.sri.springrevise.service.sql;

import com.sri.springrevise.model.sql.PrestoCard;
import com.sri.springrevise.repository.sql.PrestoSqlRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PrestoSqlService {
    private PrestoSqlRepository prestoSqlRepository;

    public PrestoSqlService(PrestoSqlRepository prestoSqlRepository) {
        this.prestoSqlRepository = prestoSqlRepository;
    }

    @Transactional
    public Double addFunds(String cardId, Double amount) {
        // 1. SELECT ... FOR UPDATE (The Lock starts here)
        PrestoCard card = prestoSqlRepository.findByIdForUpdate(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found: " + cardId));

        // 2. Business Logic (Simple addition, but protected)
        Double oldBalance = card.getBalance();
        card.setBalance(oldBalance + amount);

        // 3. Log it (Senior tip: always log financial movement)
        System.out.println("Card " + cardId + " topped up. New balance: " + card.getBalance());

        // When method exits:
        // - Transaction commits
        // - SQL UPDATE is fired
        // - Lock is released
        return card.getBalance();
    }
}

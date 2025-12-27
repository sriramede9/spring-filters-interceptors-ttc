package com.sri.springrevise.model.mongo;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Data
public class User {
    @Id
    private String id;
    private String username;
    private String password; // This MUST be hashed (BCrypt)
    private List<String> roles; // e.g., ["ROLE_USER", "ROLE_ADMIN"]
}
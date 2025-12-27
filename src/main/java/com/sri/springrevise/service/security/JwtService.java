package com.sri.springrevise.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private String secretString = "my-ultra-secure-32-character-secret-key-for-ontario";

    public String generateToken(UserDetails userDetails) {
        // 1. Get roles from the UserDetails object
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // 2. Modern JJWT 0.12+ syntax
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", roles) // 🔥 This is what PreAuthorize needs!
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(getSigningKey()) // Algorithm is inferred from key type
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey()) // Your secret key
                .build()
                .parseClaimsJws(token)         // This throws an error if signature is invalid
                .getBody()
                .getSubject();                 // This returns the "username"
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(java.util.Base64.getEncoder().encodeToString(secretString.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public List<GrantedAuthority> extractAuthorities(String token) {
        // 1. Extract all claims from the token
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 2. Get the roles list (we stored it under the key "roles")
        // Note: If you stored them as strings, we cast them here
        List<String> roles = claims.get("roles", List.class);

        // 3. Convert List<String> to List<GrantedAuthority>
        // Spring Security needs "SimpleGrantedAuthority" objects
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public boolean isTokenValid(String token) {
        try {
            // This line checks the signature AND the expiration
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return true; // If no exception is thrown, it's valid!
        } catch (JwtException | IllegalArgumentException e) {
            // In a Senior role, you'd log this: "Invalid JWT signature/claims"
            return false;
        }
    }
}
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
                .verifyWith(getSigningKey()) // Replaces setSigningKey()
                .build()
                .parseSignedClaims(token)    // Replaces parseClaimsJws()
                .getPayload()               // Replaces getBody()
                .getSubject();              // Returns the "sub" claim
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(java.util.Base64.getEncoder().encodeToString(secretString.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public List<GrantedAuthority> extractAuthorities(String token) {
        // 1. Extract all claims using the modern parser
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey()) // Replaces setSigningKey
                .build()
                .parseSignedClaims(token)    // Replaces parseClaimsJws
                .getPayload();               // Replaces getBody

        // 2. Get the roles list safely
        // Senior Tip: If "roles" is null, return an empty list to avoid NPE
        List<?> roles = claims.get("roles", List.class);
        if (roles == null) {
            return List.of();
        }

        // 3. Convert List to List<GrantedAuthority>
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
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
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
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    // 🏗️ The "Master Builder" - Unified logic
    private String buildToken(String subject, List<String> roles) {
        return Jwts.builder().subject(subject).claim("roles", roles).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(getSigningKey()).compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().verifyWith(getSigningKey()) // Replaces setSigningKey()
                .build().parseSignedClaims(token)    // Replaces parseClaimsJws()
                .getPayload()               // Replaces getBody()
                .getSubject();              // Returns the "sub" claim
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(java.util.Base64.getEncoder().encodeToString(secretString.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public List<GrantedAuthority> extractAuthorities(String token) {
        // 1. Extract all claims using the modern parser
        Claims claims = Jwts.parser().verifyWith(getSigningKey()) // Replaces setSigningKey
                .build().parseSignedClaims(token)    // Replaces parseClaimsJws
                .getPayload();               // Replaces getBody

        // 2. Get the roles list safely
        // Senior Tip: If "roles" is null, return an empty list to avoid NPE
        List<?> roles = claims.get("roles", List.class);
        if (roles == null) {
            return List.of();
        }

        // 3. Convert List to List<GrantedAuthority>
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList());
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey()) // Replaces setSigningKey()
                    .build()
                    .parseSignedClaims(token);   // Replaces parseClaimsJws()

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Senior Tip: On your M4 Mac, use a Logger here instead of just returning false
            // e.g., log.error("JWT validation failed: {}", e.getMessage());
            return false;
        }
    }

    // BIRD 1: Manual/MongoDB Login
    public String generateToken(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return buildToken(userDetails.getUsername(), roles);
    }

    // For Google login
// BIRD 2: Google/OAuth2 Login
    public String generateToken(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");

        // Senior Note: OAuth2 users usually have a default 'ROLE_USER'
        // unless you fetch custom roles from your DB.
        List<String> roles = List.of("ROLE_USER");

        return buildToken(email, roles);
    }
}
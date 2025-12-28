package com.sri.springrevise.controller.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> sayHello(Authentication authentication) {
        // This 'authentication' is populated by your JwtFilter
        String email = authentication.getName(); 
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello " + email + "!");
        response.put("role", authentication.getAuthorities().toString());
        
        return ResponseEntity.ok(response);
    }
}
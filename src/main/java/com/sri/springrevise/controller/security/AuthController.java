package com.sri.springrevise.controller.security;

import com.sri.springrevise.model.security.LoginRequest;
import com.sri.springrevise.service.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService; // We will create this next

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        // 1. Authenticate the user against the Database
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 2. If successful, generate a Token
        if (authentication.isAuthenticated()) {
            // Cast the principal to UserDetails to get the roles
            UserDetails user = (UserDetails) authentication.getPrincipal();
            return jwtService.generateToken(user); // Pass the whole user object
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
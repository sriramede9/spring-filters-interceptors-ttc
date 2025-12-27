package com.sri.springrevise.service.mongo;

import com.sri.springrevise.model.mongo.User;
import com.sri.springrevise.model.security.UserRegistrationDto;
import com.sri.springrevise.repository.mongo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(UserRegistrationDto registrationDto) {
        // 1. Check if user already exists
        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists!");
        }

        // 2. Create Entity
        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());

        // 🔥 CRITICAL: Hash the password before saving
        String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
        newUser.setPassword(encodedPassword);

        // 3. Assign Default Roles (The "Authorities")
        newUser.setRoles(registrationDto.getRoles());

        userRepository.save(newUser);
        return "User registered successfully!";
    }
}
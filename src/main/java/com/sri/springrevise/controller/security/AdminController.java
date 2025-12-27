package com.sri.springrevise.controller.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')") // 🔥 This checks the 'authorities' we extracted in the filter
    public String getAdminDashboard() {
        return "Welcome to the Senior Admin Dashboard. Your JWT works!";
    }
}
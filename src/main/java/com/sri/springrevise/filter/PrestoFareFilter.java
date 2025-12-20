package com.sri.springrevise.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class PrestoFareFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String prestoToken = request.getHeader("X-Presto-Token");

        if (prestoToken == null || prestoToken.isEmpty()) {
            // No fare? No entry to the bus.
            response.sendError(HttpServletResponse.SC_PAYMENT_REQUIRED, "Tap your Presto card!");
            return;
        }

        // Fare valid! Move to the next step in the chain.
        filterChain.doFilter(request, response);
    }
}
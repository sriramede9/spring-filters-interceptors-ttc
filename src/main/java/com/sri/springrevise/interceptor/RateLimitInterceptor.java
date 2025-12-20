package com.sri.springrevise.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    // Simple in-memory limit: 5 requests per minute per "Presto Card"
    private final Map<String, Integer> requestCounts = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String prestoToken = request.getHeader("X-Presto-Token");
        
        int count = requestCounts.getOrDefault(prestoToken, 0);
        if (count >= 5) {
            response.setStatus(429); // Too Many Requests
            response.getWriter().write("Bus is full! Please wait for the next one (Rate limit exceeded).");
            return false; 
        }

        requestCounts.put(prestoToken, count + 1);
        return true;
    }
}
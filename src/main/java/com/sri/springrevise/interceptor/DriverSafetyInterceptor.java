package com.sri.springrevise.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class DriverSafetyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("Driver: Checking for strollers and priority seating...");
        
        // If we want to check a specific 'Route' (Controller)
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            System.out.println("Heading to destination: " + method.getMethod().getName());
        }

        return true; // Return true to let the passenger stay on
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("Driver: Looking at the traffic light before opening doors.");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("Driver: Passenger is out. Closing doors and clearing logs.");
    }
}
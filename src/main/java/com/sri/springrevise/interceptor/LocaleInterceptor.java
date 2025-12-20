package com.sri.springrevise.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LocaleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String language = request.getHeader("Accept-Language");
        
        if ("fr".equalsIgnoreCase(language)) {
            request.setAttribute("greeting", "Bienvenue à la station Union!");
        } else {
            request.setAttribute("greeting", "Welcome to Union Station!");
        }
        
        return true;
    }
}
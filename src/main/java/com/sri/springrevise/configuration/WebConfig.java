package com.sri.springrevise.configuration;

import com.sri.springrevise.interceptor.DriverSafetyInterceptor;
import com.sri.springrevise.interceptor.LocaleInterceptor;
import com.sri.springrevise.interceptor.RateLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private DriverSafetyInterceptor driverInterceptor;

    @Autowired
    private LocaleInterceptor localeInterceptor;

    @Autowired
    private RateLimitInterceptor rateLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1. check if the capacity is full
//        registry.addInterceptor(rateLimitInterceptor).addPathPatterns("/api/ttc/**");
//        // 2. Then decide what language to speak
//        registry.addInterceptor(localeInterceptor).addPathPatterns("/api/ttc/**");
//        // 3. then check for payment
//        registry.addInterceptor(driverInterceptor)
//                .addPathPatterns("/api/ttc/**");
    }
}
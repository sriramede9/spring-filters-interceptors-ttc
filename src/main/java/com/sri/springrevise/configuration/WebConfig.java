package com.sri.springrevise.configuration;

import com.sri.springrevise.interceptor.DriverSafetyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private DriverSafetyInterceptor driverInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Tell the driver to monitor all TTC routes
        registry.addInterceptor(driverInterceptor)
                .addPathPatterns("/api/ttc/**"); 
    }
}
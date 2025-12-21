package com.sri.springrevise.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.transit")
@Data
public class TransitProperties {
    private List<String> allowedStops;
}
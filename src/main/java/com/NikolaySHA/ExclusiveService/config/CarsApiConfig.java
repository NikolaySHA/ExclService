package com.NikolaySHA.ExclusiveService.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cars.api")
@Getter
@Setter
public class CarsApiConfig {
    private String baseUrl;
    
}

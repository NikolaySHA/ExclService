package com.NikolaySHA.ExclusiveService.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "expenses.api")
@Getter
@Setter
public class ExpensesApiConfig {
    private String baseUrl;
    
}

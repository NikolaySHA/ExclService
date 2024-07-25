package com.NikolaySHA.ExclusiveService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfig {
//    @Bean("genericRestClient")
//    public RestClient genericRestClient(){
//        return RestClient.create();
//    }
    @Bean("expensesRestClient")
    public RestClient expensesRestClient(ExpensesApiConfig expensesApiConfig) {
        return RestClient
                .builder()
                .baseUrl(expensesApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}

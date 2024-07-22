package com.NikolaySHA.ExclusiveService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfig {
    @Bean("genericRestClient")
    public RestClient genericRestClient(){
        return RestClient.create();
    }
    @Bean("usersRestClient")
    public RestClient usersRestClient(CarsApiConfig carsApiConfig) {
        return RestClient
                .builder()
                .baseUrl(carsApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}

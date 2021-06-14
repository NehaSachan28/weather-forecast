package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Config class to initialize beans and properties
 */

@Configuration
@ConfigurationProperties
@Data
public class WeatherForecastConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    private String weatherForecastApi;
}

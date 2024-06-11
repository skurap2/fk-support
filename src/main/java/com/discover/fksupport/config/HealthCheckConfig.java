package com.discover.fksupport.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class HealthCheckConfig {

    @Bean
    @ConfigurationProperties(prefix = "service.urls")
    public Map<String, Map<String, String>> serviceUrls() {
        return new HashMap<>();
    }
}

package com.discover.fksupport.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "service")
public class ConsumerServiceModel {

    private Map<String, Map<String, String>> urls;
}


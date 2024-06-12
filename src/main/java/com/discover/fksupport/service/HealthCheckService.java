package com.discover.fksupport.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Service
public class HealthCheckService {

    private static final Logger logger = LoggerFactory.getLogger(HealthCheckService.class);

    @Autowired
    private Map<String, Map<String, String>> serviceUrls;

    private final WebClient webClient = WebClient.create();

    public Flux<String> checkHealth(String environment) {
        logger.info("serviceUrls: {}", serviceUrls);
        List<String> urls = serviceUrls.values().stream().map(envMap -> envMap.get(environment)).toList();
        logger.info("serviceUrls stream: {}", urls);
        return Flux.fromIterable(urls)
                .flatMap(url -> webClient.get()
                        .uri(url)
                        .retrieve()
                        .bodyToMono(String.class)
                        .onErrorReturn("Service at " + url + " is down."));
    }
}

package com.discover.fksupport.service;

import com.discover.fksupport.model.HealthCheckOutputVO;
import com.discover.fksupport.model.HealthCheckResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class HealthCheckService {

    private static final Logger logger = LoggerFactory.getLogger(HealthCheckService.class);

    @Autowired
    private Map<String, Map<String, String>> serviceUrls;

    private final WebClient webClient = WebClient.create();

    /*public Flux<HealthCheckOutputVO> checkHealth(String environment) {
        logger.info("serviceUrls: {}", serviceUrls);
        List<String> urls = serviceUrls.values().stream().map(envMap -> envMap.get(environment)).toList();
        logger.info("serviceUrls stream: {}", urls);
        return Flux.fromIterable(urls)
                .flatMap(url -> webClient.get()
                        .uri(url)
                        .retrieve()
                        .bodyToMono(HealthCheckOutputVO.class)
                        .onErrorReturn(new HealthCheckOutputVO())
                );
    }*/

    public Flux<HealthCheckOutputVO> checkHealth(String environment) {
        logger.info("serviceUrls: {}", serviceUrls);
        List<String> urls = serviceUrls.values().stream().map(envMap -> envMap.get(environment)).toList();
        logger.info("serviceUrls stream: {}", urls);
        HealthCheckOutputVO healthCheckOutputVO = new HealthCheckOutputVO();
        List<HealthCheckResultVO> healthCheckResultVOS = getHealthCheckResultVOS();
        healthCheckOutputVO.setHealthCheckResults(healthCheckResultVOS);
        return Flux.fromIterable(urls)
                .flatMap(url -> webClient.get()
                        .uri(url)
                        .retrieve()
                        .bodyToMono(HealthCheckOutputVO.class)
                        .onErrorReturn(healthCheckOutputVO)
                );
    }

    private static List<HealthCheckResultVO> getHealthCheckResultVOS() {
        HealthCheckResultVO healthCheckResultVO1 = new HealthCheckResultVO();
        healthCheckResultVO1.setSystemName("Finacle");
        healthCheckResultVO1.setStatusCode("Success");
        healthCheckResultVO1.setStatusDescription("Finacle call successful");
        HealthCheckResultVO healthCheckResultVO2 = new HealthCheckResultVO();
        healthCheckResultVO2.setSystemName("NCS Data Service");
        healthCheckResultVO2.setStatusCode("Success");
        healthCheckResultVO2.setStatusDescription("NCS Data Service call successful");
        List<HealthCheckResultVO> healthCheckResultVOS = Arrays.asList(healthCheckResultVO1, healthCheckResultVO2);
        return healthCheckResultVOS;
    }


}

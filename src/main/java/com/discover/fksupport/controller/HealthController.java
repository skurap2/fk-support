package com.discover.fksupport.controller;

import com.discover.fksupport.model.ConsumerServiceModel;
import com.discover.fksupport.model.HealthRequestModel;
import com.discover.fksupport.model.HealthResponseModel;
import com.discover.fksupport.service.HealthCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class HealthController {

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    private final ConsumerServiceModel consumerServiceModel;
    private final HealthCheckService healthCheckService;

    @Autowired
    public HealthController(ConsumerServiceModel consumerServiceModel, HealthCheckService healthCheckService) {
        this.consumerServiceModel = consumerServiceModel;
        this.healthCheckService = healthCheckService;
    }

    @GetMapping("/properties")
    public String showProperties(Model model) {
        model.addAttribute("consumerServices", consumerServiceModel);
        logger.info("Properties in Controller: {}", consumerServiceModel.getUrls());
        return "properties";
    }

    @GetMapping({"/", "/home", "/select-environment"})
    public String selectEnvironment(Model model) {
        model.addAttribute("healthRequest", new HealthRequestModel());
        return "environment-form";
    }

    @PostMapping("/health-check")
    public Mono<String> healthCheck(@ModelAttribute("healthRequest") HealthRequestModel healthRequest, Model model) {
        HealthResponseModel healthResponse = new HealthResponseModel();
        healthResponse.setEnvironment(healthRequest.getEnvironment());
        model.addAttribute("healthResponse", healthResponse);
        return Mono.just("health-response");
    }

    @GetMapping("/healthcheck")
    public String getHealthChecks(@RequestParam String environment, Model model) {
        Flux<String> healthCheckResults = healthCheckService.checkHealth(environment);
        Mono<List<String>> healthCheckResultsList = healthCheckResults.collectList();
        healthCheckResultsList.subscribe(results -> model.addAttribute("results", results));
        /*List<String> results = healthCheckResults.collectList().block();
        model.addAttribute("results", results);*/
        return "healthcheck";
    }


}


package com.discover.fksupport.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class HealthCheckOutputVO {
    private List<HealthCheckResultVO> healthCheckResults;
    private BuildInfoVO buildInfo;
}
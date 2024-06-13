package com.discover.fksupport.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HealthCheckResultVO {
    private String systemName;
    private String statusCode;
    private String statusDescription;
}

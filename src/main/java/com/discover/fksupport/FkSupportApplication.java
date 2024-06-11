package com.discover.fksupport;

import com.discover.fksupport.model.ConsumerServiceModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConsumerServiceModel.class)
public class FkSupportApplication {

	public static void main(String[] args) {
		SpringApplication.run(FkSupportApplication.class, args);
	}

}

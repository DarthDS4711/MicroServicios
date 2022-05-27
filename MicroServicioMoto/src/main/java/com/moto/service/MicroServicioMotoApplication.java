package com.moto.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroServicioMotoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServicioMotoApplication.class, args);
	}

}

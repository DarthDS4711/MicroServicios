package com.user.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroServicioUsuarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServicioUsuarioApplication.class, args);
	}

}

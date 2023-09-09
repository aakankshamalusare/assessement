package com.example.fxtrading.entrypoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.fxtrading.controller","com.example.fxtrading.service"})
public class FxtradingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FxtradingApplication.class, args);
	}

}

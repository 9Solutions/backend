package com.example.apirestv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
public class ApiRestV2Application {
	public static void main(String[] args) {
		SpringApplication.run(ApiRestV2Application.class, args);
	}
}

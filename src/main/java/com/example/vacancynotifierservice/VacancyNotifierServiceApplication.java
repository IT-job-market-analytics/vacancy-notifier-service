package com.example.vacancynotifierservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class VacancyNotifierServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacancyNotifierServiceApplication.class, args);
	}

}

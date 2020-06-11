package com.invicto.vaconfigureservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class VirtualApisConfigureServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualApisConfigureServiceApplication.class, args);
	}

}

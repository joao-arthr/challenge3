package com.compass.statemanegement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StateManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(StateManagementApplication.class, args);
	}

}

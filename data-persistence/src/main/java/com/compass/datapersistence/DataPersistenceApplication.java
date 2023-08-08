package com.compass.datapersistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DataPersistenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataPersistenceApplication.class, args);
	}

}

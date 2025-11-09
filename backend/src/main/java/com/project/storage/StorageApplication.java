package com.project.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class StorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageApplication.class, args);
	}

}

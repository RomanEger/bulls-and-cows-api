package org.example.bullsandcowsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("org.example.bullsandcowsapi.repository")
public class BullsAndCowsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BullsAndCowsApiApplication.class, args);
	}

}

package com.mermer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRestApiMermerNeo4jApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringRestApiMermerNeo4jApplication.class);
		app.run(args);
	}

}

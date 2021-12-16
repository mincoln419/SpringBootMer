package com.mermer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringMermerShopMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMermerShopMvcApplication.class, args);
	}

}

package com.mermer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class SpringBootServerApplication {
			
//	@CrossOrigin(origins = "http://localhost:18080")
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootServerApplication.class, args);
	}

}

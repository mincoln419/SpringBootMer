package com.mermer.cm.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CMACController {

	
	@GetMapping("/account")
	public List<String> getAccount() {
		log.debug("GET / HTTP/1.1");
		return Arrays.asList("snow", "elsa", "olaf");
	}
}

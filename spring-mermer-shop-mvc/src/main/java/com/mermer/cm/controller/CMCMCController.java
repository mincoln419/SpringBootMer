package com.mermer.cm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CMCMCController {

	
	@GetMapping("/")
	public String getIndex() {
		log.debug("GET / HTTP/1.1");
		return "hello";
	}
	
}

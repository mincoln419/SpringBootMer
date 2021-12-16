package com.mermer.cm.controller;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mermer.cm.entity.TB_CMAC_ACOUNT;
import com.mermer.cm.repository.AccountRepository;
import com.mermer.cm.service.AccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/account")
@RequiredArgsConstructor
public class CMACController {

	private final AccountService accountService;
	
	@GetMapping
	public List<String> getAccount() {
		log.debug("GET /account HTTP/1.1");
		return Arrays.asList("snow", "elsa", "olaf");
	}

	@GetMapping("/new")
	public List<TB_CMAC_ACOUNT> newAccount() {
		log.debug("GET /account/new HTTP/1.1");
		List<TB_CMAC_ACOUNT> result = accountService.createAccount();
		return result;
	}
}

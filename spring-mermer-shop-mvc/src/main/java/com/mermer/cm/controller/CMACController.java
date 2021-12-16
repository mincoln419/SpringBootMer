package com.mermer.cm.controller;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mermer.cm.dto.AccountDto;
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

	@PostMapping
	public ResponseEntity newAccount(@Validated @RequestBody AccountDto accountDto) {
		log.debug("GET /account/new HTTP/1.1");
		ResponseEntity result = accountService.createAccount(accountDto);
		return result;
	}
/*	public List<TB_CMAC_ACOUNT> newAccount(@Validated @RequestBody AccountDto accountDto) {
		log.debug("GET /account/new HTTP/1.1");
		List<TB_CMAC_ACOUNT> result = accountService.createAccount(accountDto);
		return result;
	}*/ //리스트 형태로 return 하게 되면 status 가 200이 된다 201이 되게 할려면 ResponseEntity 로 리턴해야 한다 
}

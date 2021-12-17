package com.mermer.cm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.dto.AccountDto;
import com.mermer.cm.exception.ErrorsResource;
import com.mermer.cm.service.AccountService;
import com.mermer.cm.validator.AccountValidator;

import static com.mermer.cm.exception.ErrorsResource.badRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/account", produces = MediaTypes.HAL_JSON_VALUE)
public class AccountController {

	private final AccountService accountService;
	private final AccountValidator accountValidator;
	
	@GetMapping
	public ResponseEntity getAccount(Pageable pageable, 
			PagedResourcesAssembler<Account> assembler) {
		log.debug("GET /account HTTP/1.1");
		
		ResponseEntity result = accountService.getAccountAll(pageable, 
				 assembler);
		
		return result;
	}

	@PostMapping
	public ResponseEntity newAccount(@RequestBody @Validated AccountDto accountDto
									, Errors errors) 
	{
		if(errors.hasErrors()) return badRequest(errors);
		
		accountValidator.accountValidate(accountDto, errors);
		if(errors.hasErrors()) return badRequest(errors);
		
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

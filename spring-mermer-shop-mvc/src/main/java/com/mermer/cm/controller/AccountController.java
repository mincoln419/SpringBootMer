package com.mermer.cm.controller;

import static com.mermer.cm.exception.ErrorsResource.badRequest;
import static com.mermer.cm.exception.ErrorsResource.unAuthorizedRequest;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.Serializable;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.dto.AccountDto;
import com.mermer.cm.entity.type.AccountRole;
import com.mermer.cm.exception.ErrorsIpml;
import com.mermer.cm.exception.ErrorsResource;
import com.mermer.cm.service.AccountService;
import com.mermer.cm.util.AccountAdapter;
import com.mermer.cm.util.CurrentUser;
import com.mermer.cm.util.DevUtil;
import com.mermer.cm.validator.AccountValidator;

import antlr.Parser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @packageName : com.mermer.cm.validator
 * @fileName : AccountController.java 
 * @author : Mermer 
 * @date : 2021.12.16 
 * @description : 계정정보 Contorller
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.16 Mermer 최초 생성
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/account", produces = MediaTypes.HAL_JSON_VALUE)
@SuppressWarnings("rawtypes")
public class AccountController {

	private final AccountService accountService;
	private final AccountValidator accountValidator;
	private final ModelMapper modelMapper;
	
	/**@param Pageable
	 * @param PagedResourcesAssembler<Account>
	 * @method getAccountAll
	 * @return ResponseEntity
	 * @description 계정정보 전체 조회
	 */
	@GetMapping
	public ResponseEntity getAccountAll(Pageable pageable, 
			PagedResourcesAssembler<Account> assembler,
			@CurrentUser Account account
			) {
		
		if(account == null)return ResponseEntity.badRequest().build();
		log.debug("GET /account HTTP/1.1");
		log.debug("CurrentUser::" 
				+ account.getUsername() + ","
				+ account.getLogin() + ","
				+ account.getRole().contains(AccountRole.ADMIN)
				);
		if(!account.getRole().contains(AccountRole.ADMIN)) {
			Link link = linkTo(methodOn(IndexController.class)).withRel("index");
			return unAuthorizedRequest(link);
		}
		
		ResponseEntity result = accountService.getAccountAll(pageable, 
				 assembler);
		
		return result;
	}
	

	/**@param Long
	 * @method getAccount
	 * @return ResponseEntity
	 * @description 계정정보 단건 조회
	 */
	@GetMapping("/{id}")
	public ResponseEntity getAccount(@PathVariable Long id) {
		log.debug("GET /account HTTP/1.1");
		
		ResponseEntity result = accountService.getAccount(id);
		
		return result;
	}
	
	/**@param Long
	 * @method getAccount
	 * @return ResponseEntity
	 * @description 계정정보 단건 조회
	 */
	@GetMapping("/login/{id}")
	public ResponseEntity getAccountByLogin(@PathVariable String id) {
		log.debug("GET /account HTTP/1.1");
		
		ResponseEntity result = accountService.getAccountByLogin(id);
		
		return result;
	}
	
	/**@param AccountDto
	 * @param Errors
	 * @method newAccount
	 * @return ResponseEntity
	 * @description 계정정보 신규생성
	 */
	@PostMapping
	public ResponseEntity newAccount(@RequestBody @Validated AccountDto accountDto
									, Errors errors
									) 
	{
		
		/* validation start */
		if(errors.hasErrors()) return badRequest(errors);
		accountValidator.accountValidate(accountDto, errors);
		accountValidator.validate(accountDto, errors);
		if(errors.hasErrors()) return badRequest(errors);
		/* validation end */
		
		log.debug("POST /account/new HTTP/1.1");
		ResponseEntity result = accountService.createAccount(accountDto);
		
		return result;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity updateAccount(@PathVariable Long id, //PathVariable은 param 이름을 인식하므로 일치시켜야함
										@RequestBody @Validated AccountDto accountDto,
										Errors errors
										)
	{
		if(errors.hasErrors()) return badRequest(errors);
		
		accountValidator.validate(accountDto, errors);
		if(errors.hasErrors()) return badRequest(errors);
		log.debug("POST /account/new HTTP/1.1");
		ResponseEntity result = accountService.updateAccount(accountDto, id);
		
		return result;
	}

}

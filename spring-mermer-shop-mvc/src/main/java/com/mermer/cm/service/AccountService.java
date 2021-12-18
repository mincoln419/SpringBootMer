package com.mermer.cm.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mermer.cm.controller.AccountController;
import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.dto.AccountDto;
import com.mermer.cm.repository.AccountRepository;
import com.mermer.cm.resource.AccountResource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor //=> 자동으로 AccountRepository를 injection 처리
@Slf4j
@SuppressWarnings("rawtypes")
public class AccountService {
	
	private final AccountRepository accountRepository;//injection 받아야할 경우 private final로 선언
	
	private final ModelMapper modelMapper;
		
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public ResponseEntity createAccount(AccountDto accountDto) {
		Account account = modelMapper.map(accountDto, Account.class);
		
		//비밀번호 암호화
		account.setPass(this.passwordEncoder.encode(account.getPass()));
		
		Account result = accountRepository.save(account);
		
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(result.getAccountId());
		URI createdUri = selfLinkBuilder.toUri();
		//event.setId(100);
		EntityModel<Optional> accountResource = AccountResource.of(Optional.of(result));//생성자 대신 static of 사용
		accountResource.add((selfLinkBuilder).withSelfRel())
		.add(linkTo(AccountController.class).withRel("query-accounts"))
		.add(selfLinkBuilder.withRel("update-account"))
		.add(Link.of("/docs/index.html#resources-account-create").withRel("profile"));
		
		return ResponseEntity.created(createdUri).body(accountResource);
	}
	


	/**
	 * getAccountAll
	 * @param assembler 
	 * @param pageable 
	 * @return
	 * ResponseEntity
	 */
	public ResponseEntity getAccountAll(Pageable pageable
			, PagedResourcesAssembler assembler) {
		
		Page<Account> page = this.accountRepository.findAll(pageable);
		var pagedResource = assembler.toModel(page, e -> AccountResource.of(e).add(Link.of("/docs/index.html#resources-account-list").withRel("profile")));
		pagedResource.add(Link.of("/docs/index.html#resources-event-list").withRel("profile"));
		return ResponseEntity.ok(pagedResource);
	}

	/**
	 * @methond getAccount
	 * @param id
	 * @return
	 * ResponseEntity
	 * @description 
	 */
	public ResponseEntity getAccount(Long accountId) {
		Optional<Account> optionalAccount = this.accountRepository.findByAccountId(accountId);
		if(optionalAccount.isEmpty()){
			return ResponseEntity.notFound().build();
		}
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(accountId);
		Account account = optionalAccount.get();
		EntityModel<Account> accountResource = AccountResource.of(account)
											  .add((selfLinkBuilder).withSelfRel())
											  .add(Link.of("/docs/index.html#resources-account-get").withRel("profile"))
											  .add(selfLinkBuilder.withRel("update-account"));
		return ResponseEntity.ok(accountResource);
	}

	/**
	 * @method updateAccount
	 * @param accountDto
	 * @param accountId 
	 * @return
	 * ResponseEntity
	 * @description 계정을 업데이트한다
	 */
	@Transactional
	public ResponseEntity updateAccount(AccountDto accountDto, Long accountId) {
		
		Optional<Account> optionalAccount = this.accountRepository.findByAccountId(accountId);
		
		//해당 계정으로 데이터가 없는 경우 return notFound
		if(optionalAccount.isEmpty()){
			return ResponseEntity.notFound().build();
		}
		Account account = optionalAccount.get();
		//modelMapper로 dto 데이터 매핑 (입력값을 조회값에 매핑
		modelMapper.map(accountDto, account);
		accountRepository.save(account);
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(accountId);
		EntityModel<Account> accountResource = AccountResource.of(account)
											  .add((selfLinkBuilder).withSelfRel())
											  .add(Link.of("/docs/index.html#resources-account-get").withRel("profile"));
		return ResponseEntity.ok(accountResource);
	}

	
	
	/******************************************************
	 * Non-validated method
	 ******************************************************/
	/**
	 * @method getClassUri
	 * @param accountId
	 * @return
	 * URI
	 * @description 
	 */
	private WebMvcLinkBuilder getClassLink(Long accountId) {
		return linkTo(AccountController.class).slash(accountId);
	}

	/**
	 * @method createAccount
	 * @param admin
	 * void
	 * @description 최초 계정 생성시에만 사용되는 save
	 */
	public Account createAccount(Account account) {
		account.setPass(this.passwordEncoder.encode(account.getPass()));
		return accountRepository.save(account);
	}
}

package com.mermer.cm.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mermer.cm.controller.AccountController;
import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.dto.AccountDto;
import com.mermer.cm.repository.AccountRepository;
import com.mermer.cm.resource.AccountResource;
import com.mermer.cm.util.AccountAdapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor //=> 자동으로 AccountRepository를 injection 처리
@Slf4j
@SuppressWarnings("rawtypes")
public class AccountService implements UserDetailsService{
	
	private final AccountRepository accountRepository;//injection 받아야할 경우 private final로 선언
	
	private final ModelMapper modelMapper;
		
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public ResponseEntity createAccount(AccountDto accountDto) {
		Account account = modelMapper.map(accountDto, Account.class);
		
		//비밀번호 암호화
		//account.setPass(this.passwordEncoder.encode(account.getPass())); --> 비밀번호 암호화를 한번 더 꼬아서 문제였음...
		
		Account result = saveAccount(account);
		
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(result.getId());
		URI createdUri = selfLinkBuilder.toUri();
		//event.setId(100);
		EntityModel<Optional> accountResource = AccountResource.of(Optional.of(result));//생성자 대신 static of 사용
		accountResource.add((selfLinkBuilder).withSelfRel())
		.add(linkTo(AccountController.class).withRel("query-accounts"))
		.add(selfLinkBuilder.withRel("update-account"))
		.add(Link.of("/docs/account.html#resources-account-create").withRel("profile"));
		
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
		var pagedResource = assembler.toModel(page, e -> AccountResource.of(e).add(Link.of("/docs/index.html#resources-get-account").withRel("profile")));
		pagedResource.add(Link.of("/docs/account.html#resources-account-list").withRel("profile"));
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
		Optional<Account> optionalAccount = this.accountRepository.findById(accountId);
		if(optionalAccount.isEmpty()){
			return ResponseEntity.notFound().build();
		}
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(accountId);
		Account account = optionalAccount.get();
		EntityModel<Account> accountResource = AccountResource.of(account)
											  .add((selfLinkBuilder).withSelfRel())
											  .add(Link.of("/docs/account.html#resources-account-get").withRel("profile"))
											  .add(selfLinkBuilder.withRel("update-account"));
		return ResponseEntity.ok(accountResource);
	}

	/**
	 * @methond getAccount
	 * @param id
	 * @return
	 * ResponseEntity
	 * @description 
	 */
	public ResponseEntity getAccountByLogin(String loginId) {
		Optional<Account> optionalAccount = this.accountRepository.findByLogin(loginId);
		if(optionalAccount.isEmpty()){
			return ResponseEntity.notFound().build();
		}
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(loginId);
		Account account = optionalAccount.get();
		EntityModel<Account> accountResource = AccountResource.of(account)
											  .add((selfLinkBuilder).withSelfRel())
											  .add(Link.of("/docs/account.html#resources-account-login").withRel("profile"));
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
		
		Optional<Account> optionalAccount = this.accountRepository.findById(accountId);
		
		//해당 계정으로 데이터가 없는 경우 return notFound
		if(optionalAccount.isEmpty()){
			return ResponseEntity.notFound().build();
		}
		Account account = optionalAccount.get();
		//modelMapper로 dto 데이터 매핑 (입력값을 조회값에 매핑
		modelMapper.map(accountDto, account);
		//비밀번호 암호화
		saveAccount(account);
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(accountId);
		EntityModel<Account> accountResource = AccountResource.of(account)
											  .add((selfLinkBuilder).withSelfRel())
											  .add(Link.of("/docs/account.html#resources-account-get").withRel("profile"));
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
	private WebMvcLinkBuilder getClassLink(Object accountId) {
		return linkTo(AccountController.class).slash(accountId);
	}


	/**
	 * @method createAccount
	 * @param admin
	 * void
	 * @description 최초 계정 생성시에만 사용되는 save
	 */
	public Account saveAccount(Account account) {//암호화 설정 이후 저장
		account.setPass(this.passwordEncoder.encode(account.getPass()));
		return accountRepository.save(account);
	}


	/**
	 * @method loadUserByUsername
	 * @param username
	 * void
	 * @description UserDetailsService 의 구현체
	 */
	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		log.debug("loginId: " + loginId);
		Account account = accountRepository.findByLogin(loginId)
				.orElseThrow(()-> new UsernameNotFoundException(loginId));

		return new AccountAdapter(account);
	}


}

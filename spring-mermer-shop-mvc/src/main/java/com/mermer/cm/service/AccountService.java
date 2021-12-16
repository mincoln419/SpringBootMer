package com.mermer.cm.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mermer.cm.controller.CMACController;
import com.mermer.cm.dto.AccountDto;
import com.mermer.cm.entity.TB_CMAC_ACOUNT;
import com.mermer.cm.repository.AccountRepository;
import com.mermer.cm.resource.AccountResource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor //=> 자동으로 AccountRepository를 injection 처리
@Slf4j
public class AccountService {
	
	private final AccountRepository accountRepository;//injection 받아야할 경우 private final로 선언
	
	private final ModelMapper modelMapper;
	
	@Transactional
	public ResponseEntity createAccount(AccountDto accountDto) {
		TB_CMAC_ACOUNT account = modelMapper.map(accountDto, TB_CMAC_ACOUNT.class);
		log.debug("error?? where");
		TB_CMAC_ACOUNT result = accountRepository.save(account);
		List<TB_CMAC_ACOUNT> list = new ArrayList<>();
		list.add(result);
		
		WebMvcLinkBuilder selfLinkBuilder = linkTo(CMACController.class).slash(result.getAccountId());
		URI createdUri = selfLinkBuilder.toUri();
		//event.setId(100);
		EntityModel<Optional> eventResource = AccountResource.of(Optional.of(result));//생성자 대신 static of 사용
		eventResource.add(linkTo(CMACController.class).withRel("query-events"));
		eventResource.add(selfLinkBuilder.withRel("create-event"));
		eventResource.add(Link.of("/docs/index.html#resources-events-create").withRel("profile"));
		return ResponseEntity.created(createdUri).body(eventResource);
	}
	
}

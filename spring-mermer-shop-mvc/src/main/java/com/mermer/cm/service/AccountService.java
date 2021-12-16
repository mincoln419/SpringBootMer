package com.mermer.cm.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mermer.cm.entity.TB_CMAC_ACOUNT;
import com.mermer.cm.entity.type.AccountPart;
import com.mermer.cm.entity.type.AccountRole;
import com.mermer.cm.repository.AccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor //=> 자동으로 AccountRepository를 injection 처리
@Slf4j
public class AccountService {
	
	private final AccountRepository accountRepository;//injection 받아야할 경우 private final로 선언
	
	private final ModelMapper modelMapper;
	
	@Transactional
	public List<TB_CMAC_ACOUNT> createAccount() {
		TB_CMAC_ACOUNT account = TB_CMAC_ACOUNT.builder()
								.username("mermer")
								.hpNum("01012345656")
								.accountRole(AccountRole.ADMIN)
								.accountPart(AccountPart.BULLETIN)
								.email("mermer@naver.com")
								.build();
		log.debug("error?? where");
		TB_CMAC_ACOUNT result = accountRepository.save(account);
		List<TB_CMAC_ACOUNT> list = new ArrayList<>();
		list.add(result);
		return list;
	}
	
}

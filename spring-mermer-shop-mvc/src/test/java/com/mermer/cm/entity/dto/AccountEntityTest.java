package com.mermer.cm.entity.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.type.AccountPart;
import com.mermer.cm.entity.type.AccountRole;
import com.mermer.common.BaseTest;

public class AccountEntityTest extends BaseTest{
	
	@Test
	@DisplayName("CMACEntity 입력,수정시간 입력 없을 경우 - 서비스 사용안한경우")
	public void AccountEntityTestWithNoService() throws Exception {
		String name = "mermer";
;		Account account = getOneAccount(name);
		
		Account returnAccount = accountRepository.save(account);
		
		assertThat(returnAccount.getUsername()).isEqualTo(name);
		assertThat(returnAccount.getInstDtm()).isNotNull();
		assertThat(returnAccount.getMdfDtm()).isNotNull();
		//이 경우에도 시간이 입력된다. accountRepository에 save가 이루어져야 함
		System.out.println("returnAccount:" + returnAccount.getInstDtm());
	}

	/**
	 * @methond getOneAccount
	 * @return
	 * Account
	 * @description Account객체 1개 build 해서 리턴
	 */
	static public Account getOneAccount(String name) {
		
		Account account = Account.builder()
				.username(name)
				.roleCd(200)
				.accountRole(AccountRole.GOLD)
				.accountPart(AccountPart.NOTION)
				.email("mermer@naver.com")
				.hpNum("01080139108")
				.build();
		return account;
	}
	
	/**
	 * @methond getOneAccount
	 * @return
	 * Account
	 * @description Account객체 1개 build(with accountId) 해서 리턴 
	 */
	static public Account getOneAccount(String name, Long accountId) {
		
		Account account = Account.builder()
				.accountId(accountId)
				.username(name)
				.roleCd(200)
				.accountRole(AccountRole.GOLD)
				.accountPart(AccountPart.NOTION)
				.email("mermer@naver.com")
				.hpNum("01080139108")
				.build();
		return account;
	}
}

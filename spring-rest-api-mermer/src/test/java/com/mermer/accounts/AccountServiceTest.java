package com.mermer.accounts;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

	@Autowired
	AccountService accountService;
	
	@Autowired
	AccountRepository accountRepository; 
	
	@Test
	public void findByUserName() {
		//Given
		String password = "pass";
		String email = "mermer@naver.com";
		
		Account account = Account.builder()
				.email(email)
				.password("pass")
				.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
				.build()
				;
		this.accountRepository.save(account);
		//When
		UserDetailsService userDetailsService = accountService;
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		
		//Then
		assertThat(userDetails.getPassword()).isEqualTo(password);
	}
}

package com.mermer.accounts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mermer.common.BaseTest;

public class AccountServiceTest extends BaseTest{

	@Autowired
	AccountService accountService;
	
	@Autowired
	AccountRepository accountRepository; 
	
	@Autowired
	PasswordEncoder passwordEncoder; 
	
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
		this.accountService.saveAccount(account);
		//When
		UserDetailsService userDetailsService = accountService;
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		
		//Then
		assertThat(this.passwordEncoder.matches(password, account.getPassword())).isTrue();
	}
	
	@Test//(expected = UsernameNotFoundException.class)
	public void findByUserNameFail() {
		
		
		//Expected
		String username = "randommail.com";
		assertThrows(UsernameNotFoundException.class, () -> {
			accountService.loadUserByUsername(username);
		});
		
	}
}

package com.mermer.accounts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.Set;

import javax.sound.midi.Sequence;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	PasswordEncoder passwordEncoder; 
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
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
		expectedException.expect(UsernameNotFoundException.class);
		expectedException.expectMessage(Matchers.containsString(username));
		
		//When
		accountService.loadUserByUsername(username);
	}
}

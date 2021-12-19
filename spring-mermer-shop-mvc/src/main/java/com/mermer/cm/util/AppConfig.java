package com.mermer.cm.util;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.type.AccountRole;
import com.mermer.cm.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AppConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public ApplicationRunner applicationRunner() {
		return new ApplicationRunner() {
			
			@Autowired
			AccountService accountService; 
			
			@Autowired
			AppProperties appProperties;

			@Override
			public void run(ApplicationArguments args) throws Exception {	
				Account admin = Account.builder()
						.loginId(appProperties.getAdminName())
						.username(appProperties.getAdminName())
						.email("mincoln419@naver.com")
						.pass(appProperties.getAdminPass())
						.accountRole(Set.of(AccountRole.ADMIN, AccountRole.GOLD))
						.build();
				log.debug("admin::" + appProperties.getAdminName());
				log.debug("guest::" + appProperties.getGuestName());
				
				accountService.createAccount(admin);
				
				
				Account user = Account.builder()
						.loginId(appProperties.getGuestName())
						.username(appProperties.getGuestName())
						.email("mincoln0203@naver.com")
						.pass(appProperties.getGuestPass())
						.accountRole(Set.of(AccountRole.USER))
						.build();
				accountService.createAccount(user);
			}
		};
	}
}

package com.mermer.cm.util;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
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

/**
 * @packageName : com.mermer.cm.util
 * @fileName : AppConfig.java 
 * @author : Mermer 
 * @date : 2021.12.16 
 * @description : App configuration
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.16 Mermer 최초 생성
 */
@Component
@Slf4j
public class AppConfig {

	@Bean
	public ModelMapper modelMapper() {
		//모델 매핑 정책 : strict -> dto -> entity layer 변경시 없는 필드에 대해서는 entity를 엄격히 따름
		//STANDARD -> 글자가 포함됐을 경우(id 가 포함된 loginId -> id) 매핑된다... 
		//이름이 겹쳐지는 필드일 경우에는 Strict로 구성해야 함
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
			.setMatchingStrategy(MatchingStrategies.STRICT);
		return mapper;
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
				
				accountService.saveAccount(admin);
				
				
				Account user = Account.builder()
						.loginId(appProperties.getGuestName())
						.username(appProperties.getGuestName())
						.email("mincoln0203@naver.com")
						.pass(appProperties.getGuestPass())
						.accountRole(Set.of(AccountRole.USER))
						.build();
				accountService.saveAccount(user);
			}
		};
	}
}

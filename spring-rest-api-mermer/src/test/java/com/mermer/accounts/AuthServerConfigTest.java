package com.mermer.accounts;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mermer.common.BaseTest;
import com.mermer.common.TestDescription;
import com.mermer.config.AppProperties;

public class AuthServerConfigTest extends BaseTest{
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	AppProperties appProperties;
	
	@Test
	@DisplayName("인증 토큰을 발급 받는 테스트")
	public void getAuthToken() throws Exception {
			
		//Given
		//com.mermer.config.AppConfig.applicationRunner() 에서 app 통해 최초 생성되는 계정과 겹치면 중복 에러 발생
		String username = appProperties.getUserUsername(); 
		String password = appProperties.getUserPassword();
//		Account mermer = Account.builder()
//				.email(username)
//				.password(password)
//				.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
//				.build();
//		this.accountService.saveAccount(mermer);
//		
		String clientId = appProperties.getClientId();
		String clientSecret = appProperties.getClientSecret();
		
		this.mockMvc.perform(post("/oauth/token")
				.with(httpBasic(clientId, clientSecret))
				.param("username", username)
				.param("password", password)
				.param("grant_type", "password")
				)
			.andDo(print())	
			.andExpect(status().isOk())
			.andExpect(jsonPath("access_token").exists());
	
	}
}


package com.mermer.cm.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.mermer.cm.util.AppProperties;
import com.mermer.common.BaseTest;

/**
 * @packageName : com.mermer.cm.config
 * @fileName : AuthServerConfigTest.java 
 * @author : Mermer 
 * @date : 2021.12.19 
 * @description : 사용자 인증 테스트
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.19 Mermer 최초 생성
 */
public class AuthServerConfigTest extends BaseTest{
	
	@Autowired
	private AppProperties appProperties;
	
	@Test
	@DisplayName("인증토큰 발급 테스트")
	public void getAuthToken() throws Exception {
		String adminName = appProperties.getAdminName();
		String pass = appProperties.getAdminPass();
		
		String clientId = appProperties.getClientId();
		String clientSecret = appProperties.getClientSecret();
		
		System.err.println(adminName);
		System.err.println(clientId);
		
		this.mockMvc.perform(post("/oauth/token")
					.with(httpBasic(clientId, clientSecret))
				//TODO default가 username/password 임 - 수정필요함
				.param("username", adminName)
				.param("password", pass) 
				.param("grant_type", "password")
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("access_token").exists())
		;
		
	}
	


}

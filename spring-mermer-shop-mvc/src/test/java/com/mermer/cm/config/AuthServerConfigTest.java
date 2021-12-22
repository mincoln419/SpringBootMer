
package com.mermer.cm.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;

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
		.andDo(document("access-token",
			requestHeaders(
				headerWithName(HttpHeaders.AUTHORIZATION).description("Basic Auth Key")
			),
			responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
			),
			responseFields(
					fieldWithPath("access_token").description("Access Token for this API"),
					fieldWithPath("token_type").description("Access Token Type"),
					fieldWithPath("refresh_token").description("Refresh Access Token"),
					fieldWithPath("expires_in").description("Expire of this Token"),
					fieldWithPath("scope").description("Token scope")
				)
			
		));
		
	}
	


}

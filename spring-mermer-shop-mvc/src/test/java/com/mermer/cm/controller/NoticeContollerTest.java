
package com.mermer.cm.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.Set;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.dto.AccountEntityTest;
import com.mermer.cm.entity.dto.NoticeDto;
import com.mermer.cm.entity.type.AccountRole;
import com.mermer.common.BaseTest;

/**
 * @packageName : com.mermer.cm.controller
 * @fileName : NoticeContollerTest.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description : NoticeControler 테스트
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성
 */
public class NoticeContollerTest extends BaseTest {
	
	@Before
	public void init() {
		accountRepository.deleteAll();
	}
	
	@Test
	@DisplayName("Notice 생성")
	public void createNotice() throws Exception {
		//Given
		//계정생성
		
		Account account = generateAccount();
		//account = accountService.saveAccount(account);
		
		//해당 계정으로 공지사항 글 작성
		String title = "Notice Test";
		StringBuilder sb = new StringBuilder();
		File f = new File("src/test/resource/test.html");
		BufferedReader br = new BufferedReader(
				new FileReader(f, Charset.forName("UTF-8")),
				16 * 1024
				);
		String str = null;
		while((str = br.readLine())!= null) {
			sb.append(str);
		}
		
		br.close();
		
		NoticeDto notice = NoticeDto.builder()
				//.instId(account)
				//.mdfId(account)
				.title(title)
				//.content(sb.toString())
				.content("sestet")
				.build();
		
		mockMvc.perform(post("/notice")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objMapper.writeValueAsString(notice))
						.accept(MediaTypes.HAL_JSON)
						.header(HttpHeaders.AUTHORIZATION, getBearerToken(getAccessToken(account))) //포스트 픽스로 "Bearer " 없으면 인증 통과 못함
						)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("title").value(title))
			//.andExpect(jsonPath("content").value(sb.toString()))
			.andExpect(jsonPath("content").value("sestet"))
			
			;
	}
	
	@Test
	@DisplayName("공지사항에 내용이 없는 경우")
	public void createNotice400_BadRequest() throws Exception {
		//Given
		//계정생성
		String name = "newAccount";
		Account account = AccountEntityTest.getOneAccount(name);
		accountService.saveAccount(account);
		
		//해당 계정으로 공지사항 글 작성
		String title = "Notice Test";
		String str = "";
		NoticeDto notice = NoticeDto.builder()
				.title(title)
				.content(str)
				//.content("sestet")
				.build();
		mockMvc.perform(post("/notice")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(notice))
				.accept(MediaTypes.HAL_JSON)
				.header(HttpHeaders.AUTHORIZATION, getBearerToken(getAccessToken(false))) //포스트 픽스로 "Bearer " 없으면 인증 통과 못함
				)
		.andDo(print())
		.andExpect(status().isBadRequest())
		;
				
	}
	
	/**
	 * @method getAccessToken
	 * @param account
	 * @param b
	 * @return
	 * String
	 * @throws Exception 
	 * @description 
	 */
	private String getAccessToken(Account account) throws Exception {
		return getAccessToken(account.getLoginId(), account.getPass());
	}

	public String getAccessToken(boolean isAccount) throws Exception{
		// Given
		if (isAccount) {
			generateAccount();
		}
		String username = appProperties.getAdminName(); 
		String password = appProperties.getAdminPass();
		return getAccessToken(username, password);
	}
	
	public String getAccessToken(String username, String password) throws Exception {
		String clientId = appProperties.getClientId();
		String clientSecret = appProperties.getClientSecret();
		
		ResultActions perform = mockMvc.perform(post("/oauth/token").with(httpBasic(clientId, clientSecret))
				.param("username", username).param("password", password).param("grant_type", "password"));
		var responseBody = perform.andReturn().getResponse().getContentAsString();
		Jackson2JsonParser parser = new Jackson2JsonParser();

		if(parser.parseMap(responseBody).get("access_token") == null) {
			System.err.println("===============access fail");
		}
		
		return parser.parseMap(responseBody).get("access_token").toString();
	}

	/**Token(getAccessToken(true))) //포스트 픽스로 "Bear
	 * @method generateAccount
	 * void
	 * @description 
	 */
	private Account generateAccount() {
		String username = appProperties.getAdminName(); 
		String password = appProperties.getAdminPass();
		Account account = Account.builder()
				.loginId(username)
				.username(username)
				.roleCd(200)
				.hpNum("01012345678")
				.email("admin@naver.com")
				.pass(password)
				.accountRole(Set.of(AccountRole.ADMIN, AccountRole.USER))
				.build();
		//account = accountService.saveAccount(account);
		return account;
		
	}

	/**
	 * @method getBearerToken
	 * @param accessToken
	 * @return
	 * Object
	 * @description 
	 */
	public Object getBearerToken(String accessToken) {
		return "Bearer " + accessToken;
	}
}

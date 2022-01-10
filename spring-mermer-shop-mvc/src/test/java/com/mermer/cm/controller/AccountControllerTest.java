package com.mermer.cm.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.dto.AccountDto;
import com.mermer.cm.entity.type.AccountPart;
import com.mermer.cm.entity.type.AccountRole;
import com.mermer.cm.repository.NoticeReplyRepository;
import com.mermer.cm.repository.NoticeRepository;
import com.mermer.common.BaseTest;
/**
 * @packageName : com.mermer.cm.validator
 * @fileName : AccountControllerTest.java 
 * @author : Mermer 
 * @date : 2021.12.16 
 * @description : 계정정보 Contorllertest
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.16 Mermer 최초 생성
 */
public class AccountControllerTest extends BaseTest{
	
	@Autowired
	NoticeRepository noticeRepository;
	
	@Autowired
	NoticeReplyRepository noticeReplyRepository;
	
	@BeforeEach
	public void init() {
		//repo 초기화는 의존성 역순으로 제거
		noticeReplyRepository.deleteAll();
		noticeRepository.deleteAll();
		accountRepository.deleteAll();
	}
	
	@Test
	@DisplayName("계정 전부 조회")
	public void selectAccountAll() throws Exception {
		//Given
		String token = getBearerToken(getAccessToken(true));
		//When & Then
		mockMvc.perform(get("/api/account")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON)//heateos 의존성 없으면 오류
				.header(HttpHeaders.AUTHORIZATION, token) //포스트 픽스로 "Bearer " 없으면 인증 통과 못함
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("_embedded.accountList[0].username").isNotEmpty())
		.andDo(document("query-accounts", links(
					linkWithRel("self").description("link to self"),
				    linkWithRel("profile").description("link to profile")
				),
				requestHeaders(
					headerWithName(HttpHeaders.ACCEPT).description("accept header"),
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				responseHeaders(
						//headerWithName(HttpHeaders.LOCATION).description("location header"),
						headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
						//response only
						fieldWithPath("_embedded.accountList[0].id").description("Id of new account"),
												
						//request +
						fieldWithPath("_embedded.accountList[0].username").description("User name of new account"),
						fieldWithPath("_embedded.accountList[0].instDtm").description("date time of created Account"),
						fieldWithPath("_embedded.accountList[0].mdfDtm").description("date time of modified Account information"),
						fieldWithPath("_embedded.accountList[0].email").description("User email of new account"),
						fieldWithPath("_embedded.accountList[0].hpNum").description("User cellphone number of new account"),
						fieldWithPath("_embedded.accountList[0].role").description("User role level"),
						fieldWithPath("_embedded.accountList[0].part").description("working part which User participate in")
					)
				
			))
		;
	}
	
	@Test
	@DisplayName("계정 하나만 조회")
	public void selectAccountOne() throws Exception {
		//Given
		Account account = generateAccount();
		String token = getBearerToken(getAccessToken(false));
		
		//When & Then
		mockMvc.perform(get("/api/account/{id}", account.getId())
				.accept(MediaTypes.HAL_JSON)
				.header(HttpHeaders.AUTHORIZATION, token) //포스트 픽스로 "Bearer " 없으면 인증 통과 못함
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document("get-account", links(
				linkWithRel("self").description("link to self"),
			    linkWithRel("profile").description("link to profile"),
			    linkWithRel("update-account").description("link to update account")
			),
			requestHeaders(
				headerWithName(HttpHeaders.ACCEPT).description("accept header")
			),
			responseHeaders(
					//headerWithName(HttpHeaders.LOCATION).description("location header"),
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
			),
			relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
					//response only
					fieldWithPath("id").description("Id of new account"),
											
					//request +
					fieldWithPath("username").description("User name of new account"),
					fieldWithPath("instDtm").description("date time of created Account"),
					fieldWithPath("mdfDtm").description("date time of modified Account information"),
					fieldWithPath("email").description("User email of new account"),
					fieldWithPath("hpNum").description("User cellphone number of new account"),
					fieldWithPath("role").description("User role level"),
					fieldWithPath("part").description("working part which User participate in")
				)
			
		));
	}
	
	@Test
	@DisplayName("로그인 ID로 account 정보 조회")
	public void selectAccountOneByLoginId() throws Exception {
		//Given
		Account account = generateAccount();
		String token = getBearerToken(getAccessToken(false));
		
		//When & Then
		mockMvc.perform(get("/api/account/login/{id}", account.getLogin())
				.accept(MediaTypes.HAL_JSON)
				.header(HttpHeaders.AUTHORIZATION, token) //포스트 픽스로 "Bearer " 없으면 인증 통과 못함
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document("get-account-login", links(
				linkWithRel("self").description("link to self"),
			    linkWithRel("profile").description("link to profile")
			),
			requestHeaders(
				headerWithName(HttpHeaders.ACCEPT).description("accept header")
			),
			responseHeaders(
					//headerWithName(HttpHeaders.LOCATION).description("location header"),
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
			),
			relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
					//response only
					fieldWithPath("id").description("Id of new account"),
											
					//request +
					fieldWithPath("username").description("User name of new account"),
					fieldWithPath("instDtm").description("date time of created Account"),
					fieldWithPath("mdfDtm").description("date time of modified Account information"),
					fieldWithPath("email").description("User email of new account"),
					fieldWithPath("hpNum").description("User cellphone number of new account"),
					fieldWithPath("role").description("User role level"),
					fieldWithPath("part").description("working part which User participate in")
				)
			
		));
	}
	
	@Test
	@DisplayName("계정 정보 수정")
	public void updateAccount() throws Exception {
		//Given
		Account account = generateAccount();
		String token = getBearerToken(getAccessToken(false));
		
		AccountDto accountDto = modelMapper.map(account, AccountDto.class);
		String modified = account.getUsername() + "_modified";
		accountDto.setUsername(modified);
		
		//when & then
		this.mockMvc.perform(put("/api/account/{id}", account.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(accountDto))
				.accept(MediaTypes.HAL_JSON)
				.header(HttpHeaders.AUTHORIZATION, token) //포스트 픽스로 "Bearer " 없으면 인증 통과 못함
				)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("username").value(modified))
			.andExpect(jsonPath("_links.self").exists())
			.andExpect(jsonPath("_links.profile").exists())
			.andDo(document("update-account", links(
					linkWithRel("self").description("link to self"),
				    linkWithRel("profile").description("link to profile")
				),
				requestHeaders(
					headerWithName(HttpHeaders.ACCEPT).description("accept header"),
					headerWithName(HttpHeaders.CONTENT_TYPE).description("accept header")
				),
				requestFields(
						//request
						fieldWithPath("login").description("loginId for updated-account"),						
						fieldWithPath("pass").description("password for login"),
						fieldWithPath("username").description("User name of new account"),						
						fieldWithPath("email").description("User email of new account"),
						fieldWithPath("hpNum").description("User cellphone number of new account"),
						fieldWithPath("role").description("User role level"),
						fieldWithPath("part").description("working part which User participate in")
				),
				responseHeaders(
						headerWithName(HttpHeaders.CONTENT_TYPE).description("accept header")
				),
				relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
						//response only
						fieldWithPath("id").description("Id of new account"),
						fieldWithPath("instDtm").description("date time of created Account"),
						fieldWithPath("mdfDtm").description("date time of modified Account information"),
												
						//request +
						fieldWithPath("username").description("User name of new account"),
						fieldWithPath("email").description("User email of new account"),
						fieldWithPath("hpNum").description("User cellphone number of new account"),
						fieldWithPath("role").description("User role level"),
						fieldWithPath("part").description("working part which User participate in")
					)
				
			));
	}
	
	
	@Test
	@DisplayName("계정 정상 생성")
	public void createAccount() throws Exception {
		
		String name = "mermer";
		String pass = "pass";
		AccountDto accountDto = AccountDto.builder()
				.login(name)
				.pass(pass)
				.username(name)
				.hpNum("01012345656")
				.role(Set.of(AccountRole.ADMIN))
				.part(Set.of(AccountPart.BULLETIN))
				.email("mermer@naver.com")
				.build();
		
		mockMvc.perform(post("/api/account")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(accountDto)) //body parameter
				.accept(MediaTypes.HAL_JSON)//heateos 의존성 없으면 오류
				//.header(HttpHeaders.AUTHORIZATION, getBearerToken(getAccessToken(true))) //포스트 픽스로 "Bearer " 없으면 인증 통과 못함
				)
		.andExpect(status().isCreated())
		.andDo(print())
		.andExpect(jsonPath("id").exists())
		.andExpect(jsonPath("username").isNotEmpty())
		.andExpect(jsonPath("username").value(name))
		.andDo(document("create-account", 
				links(
					linkWithRel("self").description("link to self")
					, linkWithRel("query-accounts").description("link to query-events")
					, linkWithRel("update-account").description("link to update an existing event")
					, linkWithRel("profile").description("link to profile")
				),
				requestHeaders(
					headerWithName(HttpHeaders.ACCEPT).description("accept header"),
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				requestFields(
					fieldWithPath("username").description("User name of new account"),
					fieldWithPath("login").description("login ID of new account"),
					fieldWithPath("pass").description("password for login"),
					fieldWithPath("email").description("User email of new account"),
					fieldWithPath("hpNum").description("User cellphone number of new account"),
					fieldWithPath("role").description("User role level"),
					fieldWithPath("part").description("working part which User participate in")
				),
				responseHeaders(
						headerWithName(HttpHeaders.LOCATION).description("location header"),
						headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
						//response only
						fieldWithPath("id").description("Unique key of new account in system"),
												
						//request +
						fieldWithPath("login").description("login ID of new account"),
						fieldWithPath("username").description("User name of new account"),
						fieldWithPath("instDtm").description("date time of created Account"),
						fieldWithPath("mdfDtm").description("date time of modified Account information"),
						fieldWithPath("email").description("User email of new account"),
						fieldWithPath("hpNum").description("User cellphone number of new account"),
						fieldWithPath("role").description("User role level"),
						fieldWithPath("part").description("working part which User participate in")
					)
				
			));
		
	}
	
	@Test
	@DisplayName("계정 생성 오류 - 휴대전화 번호 잘못")
	public void createAcccount400_wrong_hp_num() throws JsonProcessingException, Exception {
		String name = "mermer";
		AccountDto accountDto = AccountDto.builder()
				.username(name)
				.hpNum("02312345656")
				.role(Set.of(AccountRole.ADMIN))
				.part(Set.of(AccountPart.BULLETIN))
				.email("mermer@naver.com")
				.build();
		
		mockMvc.perform(post("/api/account")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(accountDto)) //body parameter
				.accept(MediaTypes.HAL_JSON)//heateos 의존성 없으면 오류
				)
		.andExpect(status().isBadRequest())
		.andDo(print())
		.andDo(document("errors", links(
					linkWithRel("index").description("link to index")
				),
				responseHeaders(
						headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				relaxedResponseFields(
						fieldWithPath("errors[0].field").description("Access Token for this API"),
						fieldWithPath("errors[0].objectName").description("Access Token Type"),
						fieldWithPath("errors[0].code").description("Refresh Access Token"),
						fieldWithPath("errors[0].defaultMessage").description("Expire of this Token")
			)
				
			));
	}
	
	@Test
	@DisplayName("계정 생성 오류 - 이름이 없는 경우")
	public void createAcccount400_empty() throws JsonProcessingException, Exception {
		String name = "";
		AccountDto accountDto = AccountDto.builder()
				.username(name)
				.hpNum("010312345656")
				.role(Set.of(AccountRole.ADMIN))
				.part(Set.of(AccountPart.BULLETIN))
				.email("mermer@naver.com")
				.build();
		
		mockMvc.perform(post("/api/account")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(accountDto)) //body parameter
				.accept(MediaTypes.HAL_JSON)//heateos 의존성 없으면 오류
				)
		.andExpect(status().isBadRequest())
		.andDo(print());
	}
	
	/* 관리자 권한만 가능한 기능에 대한 제어 테스트*/
	@Test
	@DisplayName("일반 사용자 계정으로 전체조회시 오류")
	public void selectAccountAll_NoAdmin401() throws Exception {
		//Given
				
		//When & Then
		mockMvc.perform(get("/api/account")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON)//heateos 의존성 없으면 오류
				.header(HttpHeaders.AUTHORIZATION, getBearerToken(getAccessTokenGuest(true))) //포스트 픽스로 "Bearer " 없으면 인증 통과 못함
				)
		.andDo(print())
		.andExpect(status().isUnauthorized())
		;
	}
	
	
	/**
	 * @method getAccessTokenGuest
	 * @param b
	 * @return
	 * String
	 * @throws Exception 
	 * @description 
	 */
	private String getAccessTokenGuest(boolean isAccount) throws Exception {

		// Given
		if (isAccount) {
			generateUserAccount();
		}
		// com.mermer.config.AppConfig.applicationRunner() 에서 app 통해 최초 생성되는 계정과 겹치면 중복
		// 에러 발생
		if(appProperties == null)return "";
		String username = appProperties.getUserName(); 
		String password = appProperties.getUserPass();
		String clientId = appProperties.getClientId();
		String clientSecret = appProperties.getClientSecret();

		ResultActions perform = mockMvc.perform(post("/oauth/token").with(httpBasic(clientId, clientSecret))
				.param("username", username).param("password", password).param("grant_type", "password"));
		var responseBody = perform.andReturn().getResponse().getContentAsString();
		Jackson2JsonParser parser = new Jackson2JsonParser();

		return parser.parseMap(responseBody).get("access_token").toString();
	}


	/**
	 * @method generateUserAccount
	 * void
	 * @description 
	 */
	private Account generateUserAccount() {
		String username = appProperties.getUserName(); 
		String password = appProperties.getUserPass();
		Account account = Account.builder()
				.login(username)
				.username(username)
				.hpNum("01012345678")
				.email("admin@naver.com")
				.pass(password)
				.role(Set.of(AccountRole.USER))
				.build();
		account = accountService.saveAccount(account);
		return account;
		
	}

	public String getAccessToken(boolean isAccount) throws Exception {
		// Given
		if (isAccount) {
			generateAccount();
		}

		// com.mermer.config.AppConfig.applicationRunner() 에서 app 통해 최초 생성되는 계정과 겹치면 중복
		// 에러 발생
		if(appProperties == null)return "";
		String username = appProperties.getAdminName(); 
		String password = appProperties.getAdminPass();
		String clientId = appProperties.getClientId();
		String clientSecret = appProperties.getClientSecret();

		ResultActions perform = mockMvc.perform(post("/oauth/token").with(httpBasic(clientId, clientSecret))
				.param("username", username).param("password", password).param("grant_type", "password"));
		var responseBody = perform.andReturn().getResponse().getContentAsString();
		Jackson2JsonParser parser = new Jackson2JsonParser();

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
				.login(username)
				.username(username)
				.hpNum("01012345678")
				.email("admin@naver.com")
				.pass(password)
				.role(Set.of(AccountRole.ADMIN, AccountRole.USER))
				.build();
		account = accountService.saveAccount(account);
		return account;
		
	}

	/**
	 * @method getBearerToken
	 * @param accessToken
	 * @return
	 * Object
	 * @description 
	 */
	public String getBearerToken(String accessToken) {
		return "Bearer " + accessToken;
	}
	
}

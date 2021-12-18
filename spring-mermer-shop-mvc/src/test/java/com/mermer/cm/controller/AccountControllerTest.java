package com.mermer.cm.controller;

import static com.mermer.cm.entity.dto.AccountEntityTest.getOneAccount;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.dto.AccountDto;
import com.mermer.cm.entity.type.AccountPart;
import com.mermer.cm.entity.type.AccountRole;
import com.mermer.common.BaseTest;

public class AccountControllerTest extends BaseTest{
	
	@Autowired
	private ModelMapper mapper;
	
	@BeforeEach
	public void init() {
		this.accountRepository.deleteAll();
	}
	
	@Test
	@DisplayName("계정 전부 조회")
	public void selectAccountAll() throws Exception {
		//Given
		String name = "mermer";
		Account account = getOneAccount(name);
		accountRepository.save(account);
		//When & Then
		mockMvc.perform(get("/account")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON)//heateos 의존성 없으면 오류
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("_embedded.accountList[0].username").isNotEmpty())
		.andExpect(jsonPath("page.totalElements").value(1))
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
						fieldWithPath("_embedded.accountList[0].accountId").description("Id of new account"),
												
						//request +
						fieldWithPath("_embedded.accountList[0].username").description("User name of new account"),
						fieldWithPath("_embedded.accountList[0].instDtm").description("date time of created Account"),
						fieldWithPath("_embedded.accountList[0].mdfDtm").description("date time of modified Account information"),
						fieldWithPath("_embedded.accountList[0].roleCd").description("role code"),
						fieldWithPath("_embedded.accountList[0].email").description("User email of new account"),
						fieldWithPath("_embedded.accountList[0].hpNum").description("User cellphone number of new account"),
						fieldWithPath("_embedded.accountList[0].accountRole").description("User role level"),
						fieldWithPath("_embedded.accountList[0].accountPart").description("working part which User participate in")
					)
				
			))
		;
	}
	
	@Test
	@DisplayName("계정 하나만 조회")
	public void selectAccountOne() throws Exception {
		//Given
		String name = "mermer";
		Account account = getOneAccount(name);
		account = accountRepository.save(account);
		
		//When & Then
		mockMvc.perform(get("/account/{accountId}", account.getAccountId())
				.accept(MediaTypes.HAL_JSON))
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
					fieldWithPath("accountId").description("Id of new account"),
											
					//request +
					fieldWithPath("username").description("User name of new account"),
					fieldWithPath("instDtm").description("date time of created Account"),
					fieldWithPath("mdfDtm").description("date time of modified Account information"),
					fieldWithPath("roleCd").description("role code"),
					fieldWithPath("email").description("User email of new account"),
					fieldWithPath("hpNum").description("User cellphone number of new account"),
					fieldWithPath("accountRole").description("User role level"),
					fieldWithPath("accountPart").description("working part which User participate in")
				)
			
		));
	}
	
	@Test
	@DisplayName("계정 정보 수정")
	public void updateAccount() throws Exception {
		//Given
		String name = "mermer";
		Account account = getOneAccount(name);
		account = accountRepository.save(account);
		
		AccountDto accountDto = mapper.map(account, AccountDto.class);
		String modified = name + "_modified";
		accountDto.setUsername(modified);
		
		//when & then
		this.mockMvc.perform(put("/account/{accountId}", account.getAccountId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(accountDto))
				.accept(MediaTypes.HAL_JSON)
				//TODO .header(HttpHeaders.AUTHORIZATION, getBearerToken(getAccessToken(false))) //포스트 픽스로 "Bearer " 없으면 인증 통과 못함
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
						fieldWithPath("username").description("User name of new account"),						
						fieldWithPath("roleCd").description("role code"),
						fieldWithPath("email").description("User email of new account"),
						fieldWithPath("hpNum").description("User cellphone number of new account"),
						fieldWithPath("accountRole").description("User role level"),
						fieldWithPath("accountPart").description("working part which User participate in")
				),
				responseHeaders(
						headerWithName(HttpHeaders.CONTENT_TYPE).description("accept header")
				),
				relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
						//response only
						fieldWithPath("accountId").description("Id of new account"),
						fieldWithPath("instDtm").description("date time of created Account"),
						fieldWithPath("mdfDtm").description("date time of modified Account information"),
												
						//request +
						fieldWithPath("username").description("User name of new account"),
						fieldWithPath("roleCd").description("role code"),
						fieldWithPath("email").description("User email of new account"),
						fieldWithPath("hpNum").description("User cellphone number of new account"),
						fieldWithPath("accountRole").description("User role level"),
						fieldWithPath("accountPart").description("working part which User participate in")
					)
				
			));
	}
	
	
	@Test
	@DisplayName("계정 정상 생성")
	public void createAccount() throws Exception {
		
		String name = "mermer";
		AccountDto accountDto = AccountDto.builder()
				.username(name)
				.hpNum("01012345656")
				.roleCd(200)
				.accountRole(AccountRole.ADMIN)
				.accountPart(AccountPart.BULLETIN)
				.email("mermer@naver.com")
				.build();
		
		mockMvc.perform(post("/account")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(accountDto)) //body parameter
				.accept(MediaTypes.HAL_JSON)//heateos 의존성 없으면 오류
				)
		.andExpect(status().isCreated())
		.andDo(print())
		.andExpect(jsonPath("accountId").exists())
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
					fieldWithPath("roleCd").description("role code"),
					fieldWithPath("email").description("User email of new account"),
					fieldWithPath("hpNum").description("User cellphone number of new account"),
					fieldWithPath("accountRole").description("User role level"),
					fieldWithPath("accountPart").description("working part which User participate in")
				),
				responseHeaders(
						headerWithName(HttpHeaders.LOCATION).description("location header"),
						headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
						//response only
						fieldWithPath("accountId").description("Id of new account"),
												
						//request +
						fieldWithPath("username").description("User name of new account"),
						fieldWithPath("instDtm").description("date time of created Account"),
						fieldWithPath("mdfDtm").description("date time of modified Account information"),
						fieldWithPath("roleCd").description("role code"),
						fieldWithPath("email").description("User email of new account"),
						fieldWithPath("hpNum").description("User cellphone number of new account"),
						fieldWithPath("accountRole").description("User role level"),
						fieldWithPath("accountPart").description("working part which User participate in")
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
				.roleCd(200)
				.accountRole(AccountRole.ADMIN)
				.accountPart(AccountPart.BULLETIN)
				.email("mermer@naver.com")
				.build();
		
		mockMvc.perform(post("/account")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(accountDto)) //body parameter
				.accept(MediaTypes.HAL_JSON)//heateos 의존성 없으면 오류
				)
		.andExpect(status().isBadRequest())
		.andDo(print());
	}
	
	@Test
	@DisplayName("계정 생성 오류 - 이름이 없는 경우")
	public void createAcccount400_empty() throws JsonProcessingException, Exception {
		String name = "";
		AccountDto accountDto = AccountDto.builder()
				.username(name)
				.hpNum("010312345656")
				.roleCd(200)
				.accountRole(AccountRole.ADMIN)
				.accountPart(AccountPart.BULLETIN)
				.email("mermer@naver.com")
				.build();
		
		mockMvc.perform(post("/account")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(accountDto)) //body parameter
				.accept(MediaTypes.HAL_JSON)//heateos 의존성 없으면 오류
				)
		.andExpect(status().isBadRequest())
		.andDo(print());
	}
	
}

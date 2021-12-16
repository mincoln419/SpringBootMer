package com.mermer.entity.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.HttpHeaders;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mermer.cm.dto.AccountDto;
import com.mermer.cm.entity.type.AccountPart;
import com.mermer.cm.entity.type.AccountRole;

@SpringBootTest
@AutoConfigureMockMvc
public class CMACControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objMapper;
	
	@Test
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
		
		mockMvc.perform(post("/account/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(accountDto)) //body parameter
				.accept(MediaTypes.HAL_JSON)//heateos 의존성 없으면 오류
				)
		.andExpect(status().isCreated())
		.andDo(print())
		.andExpect(jsonPath("accountId").exists())
		.andExpect(jsonPath("username").isNotEmpty())
		.andExpect(jsonPath("username").value(name))
		.andDo(document("create-event", 
				links(
					linkWithRel("self").description("link to self")
					, linkWithRel("query-events").description("link to query-events")
					, linkWithRel("update-event").description("link to update an existing event")
					, linkWithRel("profile").description("link to profile")
				),
				requestHeaders(
					headerWithName(HttpHeaders.ACCEPT).description("accept header"),
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				requestFields(
					fieldWithPath("username").description("User name of new account"),
					fieldWithPath("description").description("description of new event"),
					fieldWithPath("instDtm").description("date time of created Account"),
					fieldWithPath("mdfDtm").description("date time of modified Account information")
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
						fieldWithPath("description").description("description of new event"),
						fieldWithPath("instDtm").description("date time of created Account"),
						fieldWithPath("mdfDtm").description("date time of modified Account information")
					)
				
			));
		;
	}
	
}


package com.mermer.cm.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.Notice;
import com.mermer.cm.entity.dto.NoticeDto;
import com.mermer.cm.entity.type.AccountRole;
import com.mermer.cm.repository.NoticeRepository;
import com.mermer.cm.service.NoticeService;
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
	
	@Autowired
	NoticeRepository noticeRepository;
	
	@Autowired
	NoticeService noticeService;
	
	@BeforeEach
	public void init() {
		noticeRepository.deleteAll();
		accountRepository.deleteAll();
	}
	
	@After
	public void after() {
		noticeRepository.deleteAll();
	}
	
	@Test
	@DisplayName("Notice 생성")
	public void createNotice() throws Exception {
		
		//밖에서 account 안 꺼낼때는 parameter에 true
		String token = getBearerToken(getAccessToken(true));
		
		
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
				.title(title)
				//.content(sb.toString())
				.content("sestet")
				.build();
		
		mockMvc.perform(post("/notice")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objMapper.writeValueAsString(notice))
						.accept(MediaTypes.HAL_JSON)
						.header(HttpHeaders.AUTHORIZATION, token)
						)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("title").value(title))
			//.andExpect(jsonPath("content").value(sb.toString()))
			.andExpect(jsonPath("content").value("sestet"))
			.andDo(document("create-notice", links(
					linkWithRel("self").description("link to self"),
				    linkWithRel("profile").description("link to profile"),
				    linkWithRel("query-notice").description("link for query notices"),
					linkWithRel("update-notice").description("link for updating the notice"),
					linkWithRel("notice-reply-create").description("link for reply to the notice")
				),
				requestHeaders(
					headerWithName(HttpHeaders.ACCEPT).description("accept header"),
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				responseHeaders(
						headerWithName(HttpHeaders.LOCATION).description("location header"),
						headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
				),
				relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
						//response only
						fieldWithPath("id").description("Id of new notice"),
												
						//request +
						fieldWithPath("title").description("title of new notice"),
						fieldWithPath("content").description("content of new notice"),
						fieldWithPath("readCnt").description("read count of new notice"),
						fieldWithPath("writerIp").description("writer IP Port of new notice"),
						fieldWithPath("instDtm").description("insert DateTime of new notice"),
						fieldWithPath("mdfDtm").description("modified DateTime of new notice"),
						fieldWithPath("inster").description("insert account ID of new notice"),
						fieldWithPath("mdfer").description("modified account ID of new notice")
						
					)
				
			))
			;
	}
	
	@Test
	@DisplayName("공지사항에 내용이 없는 경우")
	public void createNotice400_BadRequest() throws Exception {
		
		//밖에서 account 안 꺼낼때는 parameter에 true
		String token = getBearerToken(getAccessToken(true));
		
		
		//Given
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
				.header(HttpHeaders.AUTHORIZATION, token) //포스트 픽스로 "Bearer " 없으면 인증 통과 못함
				)
		.andDo(print())
		.andExpect(status().isBadRequest())
		
		;
				
	}
	
	@Test
	@DisplayName("Notice 전체 조회")
	public void queryNotice() throws Exception {
		
		Account account = generateAccount();
		//밖에서 account 꺼낼때는 parameter에 false
		String token = getBearerToken(getAccessToken(false));//토큰은 필요없음..
		
		//해당 계정으로 공지사항 글 작성
		String title = "Notice Test";
		String testDoc = "test";//getTestDoc();

		Notice notice = Notice.builder()
				.title(title)
				.content(testDoc)
				.inster(account)
				.mdfer(account)
				.writerIp("127.0.0.1")
				.build();
		noticeService.createNotice(notice);
		
		mockMvc.perform(get("/notice")
				.accept(MediaTypes.HAL_JSON)
				//.header(HttpHeaders.AUTHORIZATION, getBearerToken(getAccessToken(account))) //포스트 픽스로 "Bearer " 없으면 인증 통과 못함
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("_embedded.noticeList[0].inster").exists())
		.andExpect(jsonPath("_embedded.noticeList[0].title").value(title))
		.andExpect(jsonPath("_embedded.noticeList[0].content").value(testDoc))
		.andDo(document("query-notice", links(
				linkWithRel("self").description("link to self"),
			    linkWithRel("profile").description("link to profile")
			),
			requestHeaders(
				headerWithName(HttpHeaders.ACCEPT).description("accept header")
			),
			responseHeaders(
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
			),
			relaxedResponseFields( //응답값에 대한 엄격한 검증을 피하는 테스트 -> _links 정보, doc 정보 누락등의 경우에도 오류나므로
					//response only
					fieldWithPath("_embedded.noticeList[0].id").description("Id of new notice"),
											
					//request +
					fieldWithPath("_embedded.noticeList[0].title").description("title of new notice"),
					fieldWithPath("_embedded.noticeList[0].content").description("content of new notice"),
					fieldWithPath("_embedded.noticeList[0].readCnt").description("read count of new notice"),
					fieldWithPath("_embedded.noticeList[0].writerIp").description("writer IP Port of new notice"),
					fieldWithPath("_embedded.noticeList[0].instDtm").description("insert DateTime of new notice"),
					fieldWithPath("_embedded.noticeList[0].mdfDtm").description("modified DateTime of new notice"),
					fieldWithPath("_embedded.noticeList[0].inster").description("insert account ID of new notice"),
					fieldWithPath("_embedded.noticeList[0].mdfer").description("modified account ID of new notice")
					
				)
			
		))
		
		;
	}
	
	
	/**
	 * @method getTestDoc
	 * @return
	 * String
	 * @throws IOException 
	 * @description 
	 */
	private String getTestDoc() throws IOException {
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
		return sb.toString();
				
		
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
	public String getAccessToken(boolean isAccount) throws Exception {
		// Given
		if(isAccount) {
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
				.loginId(username)
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

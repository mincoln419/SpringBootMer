
package com.mermer.cm.controller;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.UpLoadFile;
import com.mermer.cm.entity.type.AccountRole;
import com.mermer.cm.repository.NoticeReplyRepository;
import com.mermer.cm.repository.NoticeRepository;
import com.mermer.cm.service.NoticeService;
import com.mermer.common.BaseTest;

/**
 * @packageName : com.mermer.cm.controller
 * @fileName : CommonContollerTest.java 
 * @author : Mermer 
 * @date : 2022.01.06 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.06 Mermer 최초 생성
 */
public class CommonContollerTest extends BaseTest {
	
	@Autowired
	NoticeRepository noticeRepository;
	
	@Autowired
	NoticeReplyRepository noticeReplyRepository;
	
	@Autowired
	NoticeService noticeService;

	@BeforeEach
	public void init() {
		//repo 초기화는 의존성 역순으로 제거
		noticeReplyRepository.deleteAll();
		noticeRepository.deleteAll();
		accountRepository.deleteAll();
	}
	
	@Test
	@DisplayName("index 페이지 접근 테스트")
	public void indexControllerTest() throws Exception {
		
		//When & Then
		mockMvc.perform(get("/api/")
				.accept(MediaTypes.HAL_JSON)
		)
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document("index", links(
				linkWithRel("docs").description("link to API documentation page")
			)))
		;
		
	}
	
	
	@Test
	@DisplayName("Notice 이미지 업로드")
	public void upLoadImageFile() throws Exception {
		//Given
		//Account account = generateAccount();
		//밖에서 account 꺼낼때는 parameter에 false
		String token = getBearerToken(getAccessToken(true));
		String filePath = "C:\\Users\\N\\Pictures\\developer.jpg";
		
		MockMultipartFile file = new MockMultipartFile("images", "developer.jpg", "image/jpg", new FileInputStream(filePath));
		
		String name = "mermer";
		
		//When & Then
		mockMvc.perform(multipart("/api/common/img")
				.file(file)
				.part(new MockPart("name", name.getBytes(StandardCharsets.UTF_8)))
				.accept(MediaTypes.HAL_JSON)
				.header(HttpHeaders.AUTHORIZATION, token)
		)
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("_embedded.upLoadFileList[0].name").value(name))
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


package com.mermer.view;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.mermer.cm.service.AccountService;
import com.mermer.cm.validator.AccountValidator;
import com.mermer.cm.validator.NoticeValidator;
import com.mermer.common.BaseTest;
import com.mermer.view.controller.BaseViewController;

/**
 * @packageName : com.mermer.view
 * @fileName : ViewControllerTest.java 
 * @author : Mermer 
 * @date : 2021.12.22 
 * @description : view mapping controller 테스트 - 사용자 인증때문에 BaseTest 상속받아서 처리
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.22 Mermer 최초 생성
 */
public class ViewControllerTest extends BaseTest {
	
	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("view 연동 테스트 ")
	public void viewCalling() throws Exception {
		mockMvc.perform(get("/"))
			.andDo(print())
			.andExpect(status().isOk())
			;
		
		mockMvc.perform(get("/notice"))
		.andDo(print())
		.andExpect(status().isOk())
		;
		
		mockMvc.perform(get("/bulletin"))
		.andDo(print())
		.andExpect(status().isOk())
		;
	}
	
	@Test
	@DisplayName("로그인 api 연동을 화면에서부터 진행한 경우")
	public void loginViewRequest() throws Exception {
		mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				)
			.andDo(print())
			.andExpect(status().isOk())
			;
		
	}
}

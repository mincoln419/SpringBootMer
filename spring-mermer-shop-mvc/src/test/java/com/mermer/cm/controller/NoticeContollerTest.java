
package com.mermer.cm.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.Notice;
import com.mermer.cm.entity.dto.AccountEntityTest;
import com.mermer.cm.entity.dto.NoticeDto;
import com.mermer.cm.repository.NoticeRepository;
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
	
	@Test
	@DisplayName("Notice 조회")
	public void createNotice() throws Exception {
		//Given
		//계정생성
		String name = "newAccount";
		Account account = AccountEntityTest.getOneAccount(name);
		accountRepository.save(account);
		
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
				.content(sb.toString())
				//.content("sestet")
				.build();
		
		mockMvc.perform(post("/notice")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objMapper.writeValueAsString(notice))
						.accept(MediaTypes.HAL_JSON)
						)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("title").value(title))
			.andExpect(jsonPath("content").value(sb.toString()))
			;
	}
	
	@Test
	@DisplayName("공지사항에 내용이 없는 경우")
	public void createNotice400_BadRequest() throws Exception {
		//Given
		//계정생성
		String name = "newAccount";
		Account account = AccountEntityTest.getOneAccount(name);
		accountRepository.save(account);
		
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
				)
		.andDo(print())
		.andExpect(status().isBadRequest())
		;
				
	}

}

package com.mermer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.mermer.cm.entity.TB_CMAC_ACOUNT;
import com.mermer.cm.repository.AccountRepository;

@SpringBootTest
@AutoConfigureMockMvc
class SpringEntityTest {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	MockMvc mockMvc;


	@Test
	@DisplayName("CMACEntity 입력,수정시간 입력 없을 경우 - 서비스 사용안한경우")
	public void CMACEntityTestWithNoService() throws Exception {
		String name = "mermer";
		TB_CMAC_ACOUNT account = TB_CMAC_ACOUNT.builder()
				.username(name)
				.roleCd(200)
				.email("mermer@naver.com")
				.hpNum("01080139108")
				.build();
		TB_CMAC_ACOUNT returnAccount = accountRepository.save(account);
		
		assertThat(returnAccount.getUsername()).isEqualTo(name);
		assertThat(returnAccount.getInstDtm()).isNotNull();
		assertThat(returnAccount.getMdfDtm()).isNotNull();
		//이 경우에도 시간이 입력된다. accountRepository에 save가 이루어져야 함
		System.out.println("returnAccount:" + returnAccount.getInstDtm());
	}
	
	
	
	@Test
	@DisplayName("CMACEntity 입력,수정시간 입력 없을 경우 - default 설정 - now")
	public void CMACEntityTest() throws Exception {
		// Given
		String name = "mermer";
		TB_CMAC_ACOUNT account = TB_CMAC_ACOUNT.builder()
				.username(name)
				.roleCd(200)
				.email("mermer@naver.com")
				.hpNum("01080139108")
				.build();
		// When & Then
		mockMvc.perform(get("/account/new"))
		.andDo(print())
		.andExpect(jsonPath("$[0].username").value(name))
		.andExpect(jsonPath("$[0].instDtm").exists())
		.andExpect(jsonPath("$[0].mdfDtm").exists())
		;
	}

	@Test
	@DisplayName("CMACEntity 입력,수정시간 입력 있을 경우")
	public void CMACEntityTestDtmInsert() throws Exception {
		String name = "mermer";
		LocalDateTime manulTime = LocalDateTime.of(2021, 11, 10, 22, 11, 12);
		TB_CMAC_ACOUNT account = TB_CMAC_ACOUNT.builder()
				.username(name)
				.roleCd(200)
				.email("mermer@naver.com")
				.hpNum("01080139108")
				.instDtm(manulTime)//JPA Auditing적용되면서 무시된다.
				.mdfDtm(manulTime)//JPA Auditing적용되면서 무시된다.
				.build();

		// When & Then
		accountRepository.save(account);
		mockMvc.perform(get("/account/new"))
			.andDo(print())
			.andExpect(jsonPath("$[0].username").value(name))
			.andExpect(jsonPath("$[0].instDtm").value(Matchers.not(manulTime)))
			.andExpect(jsonPath("$[0].mdfDtm").value(Matchers.not(manulTime)))
			;
	}

}

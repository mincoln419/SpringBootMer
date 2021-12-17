package com.mermer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.mermer.cm.entity.Account;
import com.mermer.cm.repository.AccountRepository;
import com.mermer.common.RestDocConfiguration;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocConfiguration.class)
@ActiveProfiles("test")
class SpringEntityTest {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	MockMvc mockMvc;


	@Test
	@DisplayName("CMACEntity 입력,수정시간 입력 없을 경우 - 서비스 사용안한경우")
	public void CMACEntityTestWithNoService() throws Exception {
		String name = "mermer";
		Account account = Account.builder()
				.username(name)
				.roleCd(200)
				.email("mermer@naver.com")
				.hpNum("01080139108")
				.build();
		Account returnAccount = accountRepository.save(account);
		
		assertThat(returnAccount.getUsername()).isEqualTo(name);
		assertThat(returnAccount.getInstDtm()).isNotNull();
		assertThat(returnAccount.getMdfDtm()).isNotNull();
		//이 경우에도 시간이 입력된다. accountRepository에 save가 이루어져야 함
		System.out.println("returnAccount:" + returnAccount.getInstDtm());
	}
}

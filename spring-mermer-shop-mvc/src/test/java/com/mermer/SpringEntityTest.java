package com.mermer;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.mermer.cm.entity.CMACEntity;

@SpringBootTest
@AutoConfigureMockMvc
class SpringEntityTest {

	@Test
	@DisplayName("CMACEntity 입력,수정시간 입력 없을 경우 - default 설정 - now")
	public void CMACEntityTest() {
		CMACEntity cmacEntity = CMACEntity.builder()
				.username("mermer")
				.roleCd(200)
				.email("mermer@naver.com")
				.hpNum("01080139108")
				.build();
		
		System.out.println(cmacEntity.getUsername());
		System.out.println(cmacEntity.getRoleCd());
		System.out.println(cmacEntity.getHpNum());
		System.out.println(cmacEntity.getInsertDtm());
		System.out.println(cmacEntity.getModifiedDtm());
						
	}
	
	@Test
	@DisplayName("CMACEntity 입력,수정시간 입력 있을 경우")
	public void CMACEntityTestDtmInsert() {
		CMACEntity cmacEntity = CMACEntity.builder()
				.username("mermer")
				.roleCd(200)
				.email("mermer@naver.com")
				.hpNum("01080139108")
				//.insertDtm(LocalDateTime.of(2021, 11, 10, 22, 11, 12))
				.modifiedDtm(LocalDateTime.of(2021, 11, 10, 22, 11, 12))
				.build();
		
		System.out.println(cmacEntity.getUsername());
		System.out.println(cmacEntity.getRoleCd());
		System.out.println(cmacEntity.getHpNum());
		System.out.println(cmacEntity.getInsertDtm());
		System.out.println(cmacEntity.getModifiedDtm());
						
	}

}

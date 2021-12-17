/**
 * @packageName : com.mermer
 * @fileName : NoticeEntityTest.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성
 */
package com.mermer.cm.entity.dto;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.rowset.serial.SerialClob;

import org.h2.jdbc.JdbcClob;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.Notice;
import com.mermer.cm.entity.type.UseYn;
import com.mermer.cm.repository.NoticeRepository;
import com.mermer.common.BaseTest;

public class NoticeEntityTest extends BaseTest{

	@Autowired
	NoticeRepository noticeRepository;
	
	
	@Test
	@DisplayName("Notice Entity 생성 테스트")
	public void NoticeTestWithNoService() throws Exception {
		
		//Given
		String name = "newAccount";
		Account account = AccountEntityTest.getOneAccount(name);
		accountRepository.save(account);
		
		String title = "mermer";
		Notice notice= Notice.builder()
					.title(title)
					.readCnt(0)
					//.useYn("Y")
					.content("대한민국은 위대하다. 이재명이 당선될까 윤석열이 당선될까 웃기지도 않는군")
					.instId(account)
					.build();
		//when
		Notice returnNotice = noticeRepository.save(notice);
		
		
		//then
		assertThat(returnNotice.getTitle()).isEqualTo(title);
		assertThat(returnNotice.getNoiceId()).isNotNull();
		assertThat(returnNotice.getInstDtm()).isNotNull();
		assertThat(returnNotice.getMdfDtm()).isNotNull();
		assertThat(returnNotice.getInstId().getUsername()).isEqualTo(name);
		assertThat(returnNotice.getInstId().getInstDtm()).isNotNull();
		assertThat(returnNotice.getUseYn()).isEqualTo(UseYn.Y);
		
	}	
	
}

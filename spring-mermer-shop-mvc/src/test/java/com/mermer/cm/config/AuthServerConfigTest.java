
package com.mermer.cm.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.mermer.cm.util.AppProperties;
import com.mermer.common.BaseTest;

/**
 * @packageName : com.mermer.cm.config
 * @fileName : AuthServerConfigTest.java 
 * @author : Mermer 
 * @date : 2021.12.19 
 * @description : 사용자 인증 테스트
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.19 Mermer 최초 생성
 */
public class AuthServerConfigTest extends BaseTest{
	
	@Autowired
	private AppProperties appProperties;
	
	@Test
	@DisplayName("인증토큰 발급 테스트")
	public void getAuthToken() {
		String adminId = appProperties.getAdminName();
		
		assertThat(adminId).isEqualTo("mermer020304191011");
	}
	
}

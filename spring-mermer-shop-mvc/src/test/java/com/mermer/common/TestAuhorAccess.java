/**
 * @packageName : com.mermer.common
 * @fileName : TestAuhorAccess.java 
 * @author : Mermer 
 * @date : 2021.12.19 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.19 Mermer 최초 생성
 */
package com.mermer.common;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.type.AccountRole;
import com.mermer.cm.service.AccountService;
import com.mermer.cm.util.AppProperties;


public class TestAuhorAccess {

	@Autowired
	static private MockMvc mockMvc;
	@Autowired
	static private AppProperties appProperties;
	@Autowired
	static private AccountService accountService;
	

}

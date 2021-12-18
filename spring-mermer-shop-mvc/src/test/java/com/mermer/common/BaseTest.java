
package com.mermer.common;

import org.junit.jupiter.api.Disabled;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mermer.cm.repository.AccountRepository;

/**
 * @packageName : com.mermer.common
 * @fileName : BaseTest.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description : 테스트에서 공통적으로 사용하는 어노테이션, 주입 bean객체
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocConfiguration.class)
@ActiveProfiles("test")
@Disabled
public class BaseTest {

	@Autowired
	protected AccountRepository accountRepository;
	
	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objMapper;
	
	@Autowired
	protected ModelMapper modelMapper;
}

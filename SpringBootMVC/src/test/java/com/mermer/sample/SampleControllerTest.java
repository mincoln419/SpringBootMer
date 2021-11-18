package com.mermer.sample;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void hello() throws Exception {
		//요청 "/"
		//응답
		/* 
		 * -모델 - username : mermer
		 * -뷰 이름 : hello
		 * 
		 * */
		
		
		mockMvc.perform(get("/hello"))
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(view().name("hello"))
		.andExpect(model().attribute("username", "mermer"));
	}
}

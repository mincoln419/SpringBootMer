package com.mermer.sample;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	WebClient webclient;
	
	@Test
	public void hello() throws Exception {
		//요청 "/"
		//응답
		/* 
		 * -모델 - username : mermer
		 * -뷰 이름 : hello
		 * 
		 * */
		
		
		mockMvc.perform(get("/helloMer"))
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(view().name("hello"))
		.andExpect(model().attribute("username", "mermer"));
	}
	
	
	@Test
	public void helloMer() throws Exception {
		
		HtmlPage page = webclient.getPage("/helloMer");
		HtmlHeading1 h1 = page.getFirstByXPath("//h1");
		assertThat(h1.getTextContent()).isEqualToIgnoringCase("mermer");
	}
}

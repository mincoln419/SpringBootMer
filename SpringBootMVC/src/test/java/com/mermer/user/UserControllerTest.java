package com.mermer.user;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@RunWith(SpringRunner.class)
@WebMvcTest(UserContorller.class)
public class UserControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	
	@Test
	public void hello() throws Exception {
		mockMvc.perform(get("/hello"))
		.andExpect(status().isOk())
		.andExpect(content().string("hello"));
		
	}
	
	@Test
	public void createUser_JSON() throws Exception {
		String userJson = "{\"username\":\"mermer\", \"age\": 27 }";
		mockMvc.perform(post("/user/create")
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.content(userJson))
			.andExpect(status().isOk())
			.andExpect((ResultMatcher) jsonPath("$.username", is(equalTo("mermer"))))
			.andExpect(jsonPath("$.age", is(equalTo(27))));
	}

	@Test
	public void createUser_XML() throws Exception {
		String userJson = "{\"username\":\"mermer\", \"age\": 27 }";
		mockMvc.perform(post("/user/create")
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.accept(MediaType.APPLICATION_XML)
		.content(userJson))
			.andExpect(status().isOk())
			.andExpect(xpath("/User/username").string("mermer"))
			.andExpect(xpath("/User/age").string("27"));
	}
	
}

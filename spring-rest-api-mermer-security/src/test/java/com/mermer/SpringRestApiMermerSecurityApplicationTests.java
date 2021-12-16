package com.mermer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.mermer.controller.HomeController;

@WebMvcTest(controllers = HomeController.class)
class SpringRestApiMermerSecurityApplicationTests {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	@WithMockUser
	public void hello() throws Exception {
		mockPrintMediaType("hello");
	}
	
	@Test
	@WithMockUser
	public void my() throws Exception {
		mockPrint("my");
	}
	
	void mockPrintMediaType(String name) throws Exception {
		mockMvc.perform(get("/" + name)
				.accept(MediaType.TEXT_HTML)
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name(name));
	}

	void mockPrint(String name) throws Exception {
		mockMvc.perform(get("/" + name)
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name(name));
	}
}

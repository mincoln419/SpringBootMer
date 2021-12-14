package com.mermer.index;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.mermer.common.BaseTest;

public class IndexControllerTest extends BaseTest{
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void index() throws Exception {
		this.mockMvc.perform(get("/api/"))
		 .andExpect(status().isOk())
		 .andExpect(jsonPath("_links.events").exists())
		 .andDo(print())
		 ;
	}

}

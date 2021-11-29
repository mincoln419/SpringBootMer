package com.mermer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mermer.events.Event;
import com.mermer.events.EventRepository;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objMapper;
	
	@MockBean
	EventRepository eventRepository;
	
	@Test
	public void createEvent() throws Exception{
		Event event = Event.builder()
				.name("Spring")
				.description("REST API Development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 22, 11, 12))
				.closeEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 23, 11, 12))
				.beginEventDateTime(LocalDateTime.of(2021, 11, 11, 12, 11, 12))
				.closeEnrollmentDateTime(LocalDateTime.of(2021, 11, 12, 13, 11, 12))
				.basePrice(100)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("서대문구 홍제동")
				.build();
		event.setId(10);
		Mockito.when(eventRepository.save(event)).thenReturn(event);
		
		
		mockMvc.perform(post("/api/events/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON_UTF8)
				.content(objMapper.writeValueAsString(event))
				)
		.andDo(print())
		.andExpect(status().isCreated())// 201
		.andExpect(jsonPath("id").exists());
	}

}

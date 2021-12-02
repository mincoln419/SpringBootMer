package com.mermer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mermer.common.TestDescription;
import com.mermer.events.Event;
import com.mermer.events.EventDto;
//import com.mermer.events.EventRepository;
import com.mermer.events.EventStatus;
import com.mermer.events.EventValidator;

/*
 * EventController Instance Test
 * 슬라이싱 테스트 -> 스프링부트 테스트
 * 
 * */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@MockMvcTest
public class EventControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objMapper;
	
	/*
	 * @MockBean EventRepository eventRepository;
	 */

	@Test
	@TestDescription("정상적으로 이벤트를 생성하는 테스트")
	public void createEvent() throws Exception {
		EventDto event = EventDto.builder()
				.name("Spring")
				.description("REST API Development with Spring")
				//.id(100)//dto를 통해서 id값을 직접 받을 수 없도록 했음
				.beginEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 22, 11, 12))
				.closeEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 23, 11, 12))
				.beginEventDateTime(LocalDateTime.of(2021, 11, 11, 12, 11, 12))
				.endEventDateTime(LocalDateTime.of(2021, 11, 12, 13, 11, 12))
				.basePrice(100)
				.maxPrice(200)
				//.eventStatus(EventStatus.PUBLISHED)
				.limitOfEnrollment(100).location("서대문구 홍제동").build();
		
		//Mockito.when(eventRepository.save(event)).thenReturn(event);

		mockMvc.perform(post("/api/events/").contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON_UTF8).content(objMapper.writeValueAsString(event))).andDo(print())
				.andExpect(status().isCreated())// 201
				.andExpect(jsonPath("id").exists()).andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
				.andExpect(jsonPath("id").value(Matchers.not(100)))
				.andExpect(jsonPath("free").value(false))
				.andExpect(jsonPath("offline").value(true))
				.andExpect(jsonPath("eventStatus").value(Matchers.not(EventStatus.DRAFT)))
				.andExpect(jsonPath("_links.self").exists())
				.andExpect(jsonPath("_links.query-events").exists())
				.andExpect(jsonPath("_links.update-event").exists())
				;
		
	}

	@Test
	@TestDescription("입력받을 수 없은 값이 들어왔을 때")
	public void createEvent_Bad_request() throws Exception {
		Event event = Event.builder().name("Spring").description("REST API Development with Spring")
				.id(100)//dto를 통해서 id값을 직접 받을 수 없도록 했음
				.beginEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 22, 11, 12))
				.closeEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 23, 11, 12))
				.beginEventDateTime(LocalDateTime.of(2021, 11, 11, 12, 11, 12))
				.endEventDateTime(LocalDateTime.of(2021, 11, 12, 13, 11, 12))
				.basePrice(100)
				.maxPrice(200)
				.free(true)
				.eventStatus(EventStatus.PUBLISHED)
				.limitOfEnrollment(100).location("서대문구 홍제동").build();
		
		//Mockito.when(eventRepository.save(event)).thenReturn(event);

		mockMvc.perform(post("/api/events/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON_UTF8)
				.content(objMapper.writeValueAsString(event))).andDo(print())
				.andExpect(status().isBadRequest());//400
				
	}
	
	@Test
	@TestDescription("필수 값이 비어있을 때")
	public void createEvent_Bad_Request_Empty_Input() throws Exception {
		EventDto eventDto = EventDto.builder().build();
		
		this.mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(this.objMapper.writeValueAsString(eventDto))
				)
			.andExpect(status().isBadRequest());
	}

	
	@Test
	@TestDescription("비즈니스 룰에 위반되는 값이 들어올 때")
	public void createEvent_Wrong_Input_request() throws Exception {
		EventDto event = EventDto.builder()
				.name("Spring")
				.description("REST API Development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 23, 11, 12))
				.closeEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 22, 11, 12)) // 시작일이 종료일보다 나중인 경우 등.. 검증?
				.beginEventDateTime(LocalDateTime.of(2021, 11, 11, 12, 11, 12))
				.endEventDateTime(LocalDateTime.of(2021, 11, 9, 13, 11, 12))
				.basePrice(10000)//값 검증..>?
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("서대문구 홍제동")
				.build();
		//Mockito.when(eventRepository.save(event)).thenReturn(event);

		mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objMapper.writeValueAsString(event)))
				.andDo(print())
				.andExpect(status().isBadRequest())//400
				.andExpect(jsonPath("$[0].objectName").exists())
				.andExpect(jsonPath("$[0].field").exists())
				.andExpect(jsonPath("$[0].defaultMessage").exists())
				.andExpect(jsonPath("$[0].code").exists())
				.andExpect(jsonPath("$[0].rejectedValue").exists());
				
	}
}

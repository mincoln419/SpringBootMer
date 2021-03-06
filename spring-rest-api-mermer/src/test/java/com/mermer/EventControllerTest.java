package com.mermer;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.IntStream;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import com.mermer.accounts.Account;
import com.mermer.accounts.AccountRepository;
import com.mermer.accounts.AccountRole;
import com.mermer.accounts.AccountService;
import com.mermer.common.BaseTest;
import com.mermer.config.AppProperties;
import com.mermer.events.Event;
import com.mermer.events.EventDto;
import com.mermer.events.EventRepository;
//import com.mermer.events.EventRepository;
import com.mermer.events.EventStatus;

/*
 * EventController Instance Test
 * ???????????? ????????? -> ??????????????? ?????????
 * 
 * */
public class EventControllerTest extends BaseTest{

	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	AppProperties appProperties;
	
	//????????? ?????? ??? ?????? ????????? ?????? ???????????? ??????
	static Account mermer;
	static String username;
	static String password;
	
	@BeforeEach
	public void setUp() throws Exception {
		this.eventRepository.deleteAll();
		this.accountRepository.deleteAll();
	}
	
	/*
	 * @MockBean EventRepository eventRepository;
	 */
	@Test
	@DisplayName("??????????????? ???????????? ???????????? ?????????")
	public void createEvent() throws Exception {
		EventDto event = EventDto.builder()
				.name("Spring")
				.description("REST API Development with Spring")
				//.id(100)//dto??? ????????? id?????? ?????? ?????? ??? ????????? ??????
				.beginEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 22, 11, 12))
				.closeEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 23, 11, 12))
				.beginEventDateTime(LocalDateTime.of(2021, 11, 11, 12, 11, 12))
				.endEventDateTime(LocalDateTime.of(2021, 11, 12, 13, 11, 12))
				.basePrice(100)
				.maxPrice(200)
				//.eventStatus(EventStatus.PUBLISHED)
				.limitOfEnrollment(100).location("???????????? ?????????").build();
		
		//Mockito.when(eventRepository.save(event)).thenReturn(event);

		mockMvc.perform(post("/api/events/")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaTypes.HAL_JSON).content(objMapper.writeValueAsString(event))
					.header(HttpHeaders.AUTHORIZATION, getBearerToken(getAccessToken(true))) //????????? ????????? "Bearer " ????????? ?????? ?????? ?????? 
					).andDo(print())
				.andExpect(status().isCreated())// 201
				.andExpect(jsonPath("id").exists()).andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
				.andExpect(jsonPath("id").value(Matchers.not(100)))
				.andExpect(jsonPath("free").value(false))
				.andExpect(jsonPath("offline").value(true))
				.andExpect(jsonPath("eventStatus").value(Matchers.not(EventStatus.DRAFT)))
				.andExpect(jsonPath("_links.self").exists())
				.andExpect(jsonPath("_links.query-events").exists())
				.andExpect(jsonPath("_links.update-event").exists())
				.andDo(document("create-event", 
					links(
						linkWithRel("self").description("link to self")
						, linkWithRel("query-events").description("link to query-events")
						, linkWithRel("update-event").description("link to update an existing event")
						, linkWithRel("profile").description("link to profile")
					),
					requestHeaders(
						headerWithName(HttpHeaders.ACCEPT).description("accept header"),
						headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
					),
					requestFields(
						fieldWithPath("name").description("name of new event"),
						fieldWithPath("description").description("description of new event"),
						fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
						fieldWithPath("closeEnrollmentDateTime").description("date time of end of new event"),
						fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
						fieldWithPath("endEventDateTime").description("date time of end of new event"),
						fieldWithPath("location").description("location of new event"),
						fieldWithPath("basePrice").description("basePrice of new event"),
						fieldWithPath("maxPrice").description("maxPrice of new event"),
						fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new event")
					),
					responseHeaders(
							headerWithName(HttpHeaders.LOCATION).description("location header"),
							headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
					),
					relaxedResponseFields( //???????????? ?????? ????????? ????????? ????????? ????????? -> _links ??????, doc ?????? ???????????? ???????????? ???????????????
							//response only
							fieldWithPath("id").description("id of new event"),
							fieldWithPath("free").description("free of new event"),
							fieldWithPath("offline").description("offline of new event"),
							fieldWithPath("eventStatus").description("eventStatus of new event"),
							
							//request +
							fieldWithPath("name").description("name of new event"),
							fieldWithPath("description").description("description of new event"),
							fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
							fieldWithPath("closeEnrollmentDateTime").description("date time of end of new event"),
							fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
							fieldWithPath("endEventDateTime").description("date time of end of new event"),
							fieldWithPath("location").description("location of new event"),
							fieldWithPath("basePrice").description("basePrice of new event"),
							fieldWithPath("maxPrice").description("maxPrice of new event"),
							fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new event")
						)
					
				));
				
		
	}

	private String getBearerToken(String accessToken) {
		return "Bearer " + accessToken;
	}

	private String getAccessToken(boolean isAccount) throws Exception {
		
		//Given
		if(isAccount) {
			generateAccount();
		}
		
		//com.mermer.config.AppConfig.applicationRunner() ?????? app ?????? ?????? ???????????? ????????? ????????? ?????? ?????? ??????
		String clientId = "myApp";
		String clientSecret = "pass";
		
		ResultActions perform = this.mockMvc.perform(post("/oauth/token")
				.with(httpBasic(clientId, clientSecret))
				.param("username", username)
				.param("password", password)
				.param("grant_type", "password")
				);
		var responseBody = perform.andReturn().getResponse().getContentAsString();
		Jackson2JsonParser parser = new Jackson2JsonParser();

		return parser.parseMap(responseBody).get("access_token").toString();
	}

	@Test
	@DisplayName("???????????? ??? ?????? ?????? ???????????? ???")
	public void createEvent_Bad_request() throws Exception {
		Event event = Event.builder().name("Spring").description("REST API Development with Spring")
				.id(100)//dto??? ????????? id?????? ?????? ?????? ??? ????????? ??????
				.beginEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 22, 11, 12))
				.closeEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 23, 11, 12))
				.beginEventDateTime(LocalDateTime.of(2021, 11, 11, 12, 11, 12))
				.endEventDateTime(LocalDateTime.of(2021, 11, 12, 13, 11, 12))
				.basePrice(100)
				.maxPrice(200)
				.free(true)
				.eventStatus(EventStatus.PUBLISHED)
				.limitOfEnrollment(100).location("???????????? ?????????").build();
		
		//Mockito.when(eventRepository.save(event)).thenReturn(event);

		mockMvc.perform(post("/api/events/")
				.header(HttpHeaders.AUTHORIZATION, getBearerToken(getAccessToken(true))) //????????? ????????? "Bearer " ????????? ?????? ?????? ??????
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON)
				.content(objMapper.writeValueAsString(event))).andDo(print())
				.andExpect(status().isBadRequest());//400
				
	}
	
	@Test
	@DisplayName("?????? ?????? ???????????? ???")
	public void createEvent_Bad_Request_Empty_Input() throws Exception {
		EventDto eventDto = EventDto.builder().build();
		
		this.mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objMapper.writeValueAsString(eventDto))
				.header(HttpHeaders.AUTHORIZATION, getBearerToken(getAccessToken(true))) //????????? ????????? "Bearer " ????????? ?????? ?????? ??????
				)
			.andExpect(status().isBadRequest());
	}

	
	@Test
	@DisplayName("???????????? ?????? ???????????? ?????? ????????? ???")
	public void createEvent_Wrong_Input_request() throws Exception {
		EventDto event = EventDto.builder()
				.name("Spring")
				.description("REST API Development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 23, 11, 12))
				.closeEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 22, 11, 12)) // ???????????? ??????????????? ????????? ?????? ???.. ???????
				.beginEventDateTime(LocalDateTime.of(2021, 11, 11, 12, 11, 12))
				.endEventDateTime(LocalDateTime.of(2021, 11, 9, 13, 11, 12))
				.basePrice(10000)//??? ??????..>?
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("???????????? ?????????")
				.build();
		//Mockito.when(eventRepository.save(event)).thenReturn(event);

		mockMvc.perform(post("/api/events")
				.header(HttpHeaders.AUTHORIZATION, getBearerToken(getAccessToken(true))) //????????? ????????? "Bearer " ????????? ?????? ?????? ??????
				.contentType(MediaType.APPLICATION_JSON)				
				.content(objMapper.writeValueAsString(event)))
				.andDo(print())
				.andExpect(status().isBadRequest())//400
				.andExpect(jsonPath("content[0].objectName").exists())
				.andExpect(jsonPath("content[0].field").exists())
				.andExpect(jsonPath("content[0].defaultMessage").exists())
				.andExpect(jsonPath("content[0].code").exists())
				.andExpect(jsonPath("content[0].rejectedValue").exists())
				.andExpect(jsonPath("_links.index").exists())
				;
		
				
	}
	
	@Test
	@DisplayName("30?????? ???????????? 10?????? ????????? ????????? ???????????? - anoymous ??? ??????")
	public void queryEvent() throws Exception{
		//Given -- ????????? 30??? ??????
//		IntStream.range(0, 30).forEach(i -> {
//			this.generateEvent(i);
//		});
		//method reference??? ?????????
		IntStream.range(0, 30).forEach(this::generateEvent);
		
		//When
		this.mockMvc.perform(get("/api/events")
				.param("page", "1")//????????? ?????????
				.param("size", "10")
				.param("sort", "name,DESC")
		)
		//Then
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("page").exists())
		.andExpect(jsonPath("_links.self").exists())
		.andExpect(jsonPath("_links.profile").exists())
		.andDo(document("query-events"))
		;
		
	}

	@Test
	@DisplayName("30?????? ???????????? 10?????? ????????? ????????? ???????????? - ????????? ???????????? ??????")
	public void queryEventWithAuthentication() throws Exception{
		//Given -- ????????? 30??? ??????
//		IntStream.range(0, 30).forEach(i -> {
//			this.generateEvent(i);
//		});
		//method reference??? ?????????
		IntStream.range(0, 30).forEach(this::generateEvent);
		
		//When
		this.mockMvc.perform(get("/api/events")
				.param("page", "1")//????????? ?????????
				.param("size", "10")
				.param("sort", "name,DESC")
				.header(HttpHeaders.AUTHORIZATION, getBearerToken(getAccessToken(true))) //????????? ????????? "Bearer " ????????? ?????? ?????? ??????
		)
		//Then
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("page").exists())
		.andExpect(jsonPath("_links.self").exists())
		.andExpect(jsonPath("_links.profile").exists())
		.andExpect(jsonPath("_links.create-event").exists())
		.andDo(document("query-events"))
		;
		
	}
	
	@Test
	@DisplayName("????????? ????????? ????????? ????????????")
	public void getEvent() throws Exception{
		//Given
		Account account = generateAccount();
		Event event = this.generateEvent(100, account);
		
		//when & then
		this.mockMvc.perform(get("/api/events/{id}", event.getId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").exists())
			.andExpect(jsonPath("name").exists())
			.andExpect(jsonPath("_links.self").exists())
			.andExpect(jsonPath("_links.profile").exists())
			.andDo(print())
			.andDo(document("get-an-event"))
		;
		
	}
	
	@Test
	@DisplayName("?????? ????????? ???????????? ??? 404 ????????????")
	public void getEvent404() throws Exception {
		//when & then
		this.mockMvc.perform(get("/api/events/11883"))
			.andExpect(status().isNotFound())
		;
	}
	
	@Test
	@DisplayName("????????? ????????? ?????? ????????????")
	public void updateEvent() throws Exception {
		
		//Given
		Account account = generateAccount();
		Event event = this.generateEvent(100, account);
		EventDto eventDto = this.modelMapper.map(event, EventDto.class);
		String eventName = event.getName() + "_modified"; 
		eventDto.setName(eventName);
		
		
		//when & then
/*		this.mockMvc.perform(get("/api/events/{id}", event.getId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").exists())
			.andExpect(jsonPath("name").exists())
			.andExpect(jsonPath("_links.self").exists())
			.andExpect(jsonPath("_links.profile").exists())
			.andDo(print())
			.andDo(document("update-an-event"))
		;*/
		
//		Event eventModified = Event.builder()
//				.id(event.getId())
//				.name(event.getName() + "_modified")
//				.description("REST API Development with Spring")
//				//.id(100)//dto??? ????????? id?????? ?????? ?????? ??? ????????? ??????
//				.beginEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 22, 11, 12))
//				.closeEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 23, 11, 12))
//				.beginEventDateTime(LocalDateTime.of(2021, 12, 11, 12, 11, 12))
//				.endEventDateTime(LocalDateTime.of(2021, 12, 12, 13, 11, 12))
//				.basePrice(100)
//				.maxPrice(200)
//				//.eventStatus(EventStatus.PUBLISHED)
//				.limitOfEnrollment(100).location("???????????? ?????????").build();
//		
		//when & then
		this.mockMvc.perform(put("/api/events/{id}", event.getId()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON).content(objMapper.writeValueAsString(eventDto))
				.header(HttpHeaders.AUTHORIZATION, getBearerToken(getAccessToken(false))) //????????? ????????? "Bearer " ????????? ?????? ?????? ??????
				)
			.andExpect(status().isOk())
			.andExpect(jsonPath("name").value(eventName))
			.andExpect(jsonPath("_links.self").exists())
			//.andExpect(jsonPath("_links.profile").exists())
			.andDo(print())
			.andDo(document("get-an-event"))
		;
	}
	@Test
	@DisplayName("????????? empty ?????? ??????")
	public void updateEvent400Empty() throws Exception {
		//Given
		Account account = generateAccount();
		Event event = this.generateEvent(200, account);
		
		EventDto eventDto = new EventDto();
		
		//when & then
		this.mockMvc.perform(put("/api/events/{id}", event.getId()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON).content(objMapper.writeValueAsString(eventDto))
				.header(HttpHeaders.AUTHORIZATION, getBearerToken(getAccessToken(false))) //????????? ????????? "Bearer " ????????? ?????? ?????? ??????
				)
			.andExpect(status().isBadRequest())//400
		;
		
	}
	
	@Test
	@DisplayName("????????? wrong ?????? ??????")
	public void updateEvent400_Wrong() throws Exception {
		//Given
		Account account = generateAccount();
		Event event = this.generateEvent(200, account);
		
		EventDto eventDto = new EventDto();
		eventDto.setBasePrice(20000);
		eventDto.setMaxPrice(1000);
		
		//when & then
		this.mockMvc.perform(put("/api/events/{id}", event.getId()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON).content(objMapper.writeValueAsString(eventDto))
				.header(HttpHeaders.AUTHORIZATION, getBearerToken(getAccessToken(false))) //????????? ????????? "Bearer " ????????? ?????? ?????? ??????
				)
			.andExpect(status().isBadRequest())//400
		;
		
	}
	
	@Test
	@DisplayName("???????????? ?????? ????????? ????????? ??????")
	public void updateEvent404() throws Exception {
		//Given
		Account account = generateAccount();
		Event event = this.generateEvent(200, account);
		
		EventDto eventDto = this.modelMapper.map(event, EventDto.class);
		
		
		//when & then
		this.mockMvc.perform(put("/api/events/123456", event.getId()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON).content(objMapper.writeValueAsString(eventDto))
				.header(HttpHeaders.AUTHORIZATION, getBearerToken(getAccessToken(false))) //????????? ????????? "Bearer " ????????? ?????? ?????? ??????
				)
			.andDo(print())
			.andExpect(status().isNotFound())//404	
		;
		
	}

	
	private Account generateAccount() {		
		username = appProperties.getUserUsername(); 
		password = appProperties.getUserPassword();
		Account account = Account.builder()
				.email(username)
				.password(password)
				.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
				.build();
		this.accountService.saveAccount(account);
		return account;
	}
	
	private Event generateEvent(int index, Account account) {
		
		//????????? ????????? manager null ?????? ?????? ?????? ??????
		Event event = Event.builder()
				.name("event" + index)
				.description("test event generated")
				//.id(100)//dto??? ????????? id?????? ?????? ?????? ??? ????????? ??????
				.beginEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 22, 11, 12))
				.closeEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 23, 11, 12))
				.beginEventDateTime(LocalDateTime.of(2021, 11, 11, 12, 11, 12))
				.endEventDateTime(LocalDateTime.of(2021, 11, 12, 13, 11, 12))
				.basePrice(100)
				.maxPrice(200)
				.manager(account)
				.free(false)
				.offline(true)
				.eventStatus(EventStatus.DRAFT)
				.limitOfEnrollment(100).location("???????????? ?????????")
				.build();
		
		return this.eventRepository.save(event);
	}

	private Event generateEvent(int index) {
		//????????? ????????? manager null ?????? ?????? ?????? ??????
		Event event = Event.builder()
				.name("event" + index)
				.description("test event generated")
				//.id(100)//dto??? ????????? id?????? ?????? ?????? ??? ????????? ??????
				.beginEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 22, 11, 12))
				.closeEnrollmentDateTime(LocalDateTime.of(2021, 11, 10, 23, 11, 12))
				.beginEventDateTime(LocalDateTime.of(2021, 11, 11, 12, 11, 12))
				.endEventDateTime(LocalDateTime.of(2021, 11, 12, 13, 11, 12))
				.basePrice(100)
				.maxPrice(200)
				.free(false)
				.offline(true)
				.eventStatus(EventStatus.DRAFT)
				.limitOfEnrollment(100).location("???????????? ?????????")
				.build();
		
		return this.eventRepository.save(event);
	}
}

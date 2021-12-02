package com.mermer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.mermer.events.Event;

/* 
 * Event Domain 테스트
 * 
 * */
public class EventTest {

	@Test
	public void builder() { // builder를 생성하면 기본 생성자가 생성이 안됨 - > 추가 어노테이션 추가 필요 -> 자바빈 스펙 준수..
		Event event = Event.builder().name("Inflearn Spring REST API").description("Inflearn Spring REST API").build();

		assertThat(event).isNotNull();
	}

	@Test
	public void javaBean() {
		// given
		Event event = new Event();
		String name = "Event";
		String description = "Spring";

		// when
		event.setName(name);
		event.setDescription(description);

		// then
		assertThat(event.getName()).isEqualTo(name);
		assertThat(event.getDescription()).isEqualTo(description);
	}

	@Test
	public void testFree() {
		// Given
		Event event = Event
				.builder()
				.basePrice(0)
				.maxPrice(0)
				.build();
		// when
		event.update();
		// then
		assertThat(event.isFree()).isTrue();

		// Given
		event = Event.builder().basePrice(100).maxPrice(0).build();
		// when
		event.update();
		// then
		assertThat(event.isFree()).isFalse();

		// Given
		event = Event.builder().basePrice(0).maxPrice(100).build();
		// when
		event.update();
		// then
		assertThat(event.isFree()).isFalse();

	}

	@Test
	public void offlineTest() {
		// Given
		Event event = Event
				.builder()
				.location("강남역 9번 출구")
				.build();
		// when
		event.update();
		// then
		assertThat(event.isOffline()).isTrue();
		
		
		// Given
		event = Event
				.builder()
				.build();
		// when
		event.update();
		// then
		assertThat(event.isOffline()).isFalse();
	}
}

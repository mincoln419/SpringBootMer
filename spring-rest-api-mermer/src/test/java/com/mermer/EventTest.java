package com.mermer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.mermer.events.Event;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/* 
 * Event Domain 테스트
 * 
 * */
@RunWith(JUnitParamsRunner.class)
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
	/*
	 * @Parameters({"0, 0 , true", "100, 0, false", "0, 100, false" })
	 * --> 문자열로 쓰는게 맘에 안들면..
	 */
	//@Parameters(method = "parametersForTestFree")
	@Parameters
	public void testFree(int basePrice, int maxPrice, boolean isFree) {
		// Given
		Event event = Event
				.builder()
				.basePrice(basePrice)
				.maxPrice(maxPrice)
				.build();
		// when
		event.update();
		// then
		assertThat(event.isFree()).isEqualTo(isFree);

	}
	//parametersFor가 convention -> @Parameters 뒤에 (methos =...)를 안써도 인식함
	//단 postfix 뒤의 이름이 동일해야 함
	private Object[] parametersForTestFree() {
		return new Object[] {
				new Object[] {0, 0 , true},
				new Object[] {100, 0 , false},
				new Object[] {0, 100 , false},
				new Object[] {200, 100 , false}
		};
	}
	
	@Test
	@Parameters
	public void testOffline(String location, boolean isOffline) {
		// Given
		Event event = Event
				.builder()
				.location(location)
				.build();
		// when
		event.update();
		// then
		assertThat(event.isOffline()).isEqualTo(isOffline);

	}
	
	private Object[] parametersForTestOffline() {
		return new Object[] {
				new Object[] {"강남역 9번출구", true},
				new Object[] {"" , false},
				new Object[] {" " , false},
				new Object[] {null , false}
		};
	}
}

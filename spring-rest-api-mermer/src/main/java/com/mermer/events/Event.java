package com.mermer.events;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id") // hashcode equal method에서 stack overflow 방지 위함 - entity 간 상호참조로 데이터가 꼬일 경우 방지 
public class Event {

	private Integer id;

	private String name;
	private String description;
	private LocalDateTime beginEnrollmentDateTime;
	private LocalDateTime closeEnrollmentDateTime;
	private LocalDateTime beginEventDateTime;
	private LocalDateTime endEventDateTime;
	private String location; // (optional) 이게 없으면 온라인 모임
	private int basePrice; // (optional)
	private int maxPrice; // (optional)
	private int limitOfEnrollment;

	private boolean offline;
	private boolean free;
	private EventStatus eventStatus;
}

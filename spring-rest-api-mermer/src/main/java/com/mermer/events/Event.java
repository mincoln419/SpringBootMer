package com.mermer.events;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mermer.accounts.Account;
import com.mermer.accounts.AccountSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* 
 * data request, response body 로 사용하기 위한 entity
 * 
 * */


@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
@Entity
public class Event {
	// hashcode equal method에서 stack overflow 방지 위함 - entity 간 상호참조로 꼬였을 경우 방지
	@Id @GeneratedValue
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
	@Enumerated(EnumType.STRING)
	private EventStatus eventStatus = EventStatus.DRAFT;
	
	@ManyToOne
	@JsonSerialize(using = AccountSerializer.class)
	private Account manager;//단방향(Account -> Event)으로 참조할 수 있도록 매핑
	
	public void update() {

		if(this.basePrice == 0 && this.maxPrice == 0) {
			this.free = true;
		}else {
			this.free = false;
		}
		
		if(this.location == null || this.location.isBlank()) { // .isBlank -> trim 후 refaction 처리해주는 11버전 기능
			this.offline = false;
		}else {
			this.offline = true;
		}
	}
}

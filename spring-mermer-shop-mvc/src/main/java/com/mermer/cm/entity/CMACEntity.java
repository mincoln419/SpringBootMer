package com.mermer.cm.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//import org.springframework.data.annotation.Id; -> No identifier specified for entity: com.mermer.cm.entity.CMACEntity 오류발생
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Validated
@NoArgsConstructor @AllArgsConstructor
@Builder @Getter @Setter @EqualsAndHashCode(of = "accountId")
public class CMACEntity {

	@Id @GeneratedValue 
	private long accountId;
	
	@NonNull
	private int roleCd;
	
	@NonNull
	private String username;
	
	@NonNull
	private String email;
	
	@NonNull
	private String hpNum;
	
	@NonNull
	@Default
	private LocalDateTime insertDtm = LocalDateTime.now();
	
	@NonNull
	@Default
	private LocalDateTime modifiedDtm = LocalDateTime.now();
	
}

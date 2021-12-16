package com.mermer.cm.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//import org.springframework.data.annotation.Id; -> No identifier specified for entity: com.mermer.cm.entity.CMACEntity 오류발생
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import com.mermer.cm.entity.type.AccountPart;
import com.mermer.cm.entity.type.AccountRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity @NoArgsConstructor @AllArgsConstructor
@Builder @Getter @Setter @EqualsAndHashCode(of = "accountId")
@EntityListeners(AuditingEntityListener.class)
public class TB_CMAC_ACOUNT {
	/* 
	 * IN-MEMORY-DB LOG
	 *  o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:06ebbc3d-c434-48bf-8a65-8ce51c56005c'
	 * */

	@Id @GeneratedValue 
	private Long accountId;
	
	private Integer roleCd;
	
	private String username;
	
	private String email;
	
	private String hpNum;
	
	@CreatedDate
	private LocalDateTime instDtm;
	
	@LastModifiedDate
	private LocalDateTime mdfDtm;
	
	@Enumerated(EnumType.STRING)
	private AccountRole accountRole;
	
	@Enumerated(EnumType.STRING)
	private AccountPart accountPart;
	
}

package com.mermer.cm.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mermer.cm.entity.type.AccountPart;
import com.mermer.cm.entity.type.AccountRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity @NoArgsConstructor @AllArgsConstructor
@Builder @Getter @Setter @EqualsAndHashCode(of = "accountId")
@EntityListeners(AuditingEntityListener.class)
public class Account {
	/* 
	 * IN-MEMORY-DB LOG
	 *  o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:06ebbc3d-c434-48bf-8a65-8ce51c56005c'
	 * */

	@Id @GeneratedValue 
	private Long accountId;
	
	private String loginId;
	
	private Integer roleCd;
	
	private String username;
	
	private String pass;
	
	private String email;
	
	private String hpNum;
	
	@CreatedDate
	private LocalDateTime instDtm;
	
	@LastModifiedDate
	private LocalDateTime mdfDtm;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)// 자주가져오고, Role 데이터가 많지 않기 때문에 EAGER 모드로 패치(set, list의 기본은 LAZY 패치)
	private Set<AccountRole> accountRole;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<AccountPart> accountPart;
	
}

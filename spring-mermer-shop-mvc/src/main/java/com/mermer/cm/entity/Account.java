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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.NonNull;

import com.mermer.cm.entity.embeded.CommonEmbeded;
import com.mermer.cm.entity.type.AccountPart;
import com.mermer.cm.entity.type.AccountRole;
import com.mermer.cm.entity.type.UseYn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity @NoArgsConstructor @AllArgsConstructor
@SuperBuilder @Getter @Setter @EqualsAndHashCode(of = "id", callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "ACCOUNT_ID_GENERATOR", sequenceName = "ACCOUNT_GENERATOR", initialValue = 1, allocationSize = 1)
public class Account extends CommonEmbeded{
	/* 
	 * IN-MEMORY-DB LOG
	 *  o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:06ebbc3d-c434-48bf-8a65-8ce51c56005c'
	 * */
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_ID_GENERATOR")
	private Long id;
	
	private String login;//loginId 규칙: 영어 + 숫자 13자 미만
	
	private String pass;//loginId 규칙: 영어 + 특수문자 + 숫자 13자 미만
		
	private String username;
	
	private String email;
	
	private String hpNum;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)// 자주가져오고, Role 데이터가 많지 않기 때문에 EAGER 모드로 패치(set, list의 기본은 LAZY 패치)
	private Set<AccountRole> role;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<AccountPart> part;
	
}

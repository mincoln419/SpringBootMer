package com.mermer.cm.entity.dto;

import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.data.relational.core.mapping.Embedded.Empty;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import com.mermer.cm.entity.type.AccountPart;
import com.mermer.cm.entity.type.AccountRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* 
 * TB_CMAC_ACOUNT 의 DTO
 * 
 * */

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AccountDto {

		@NotBlank
		private String loginId;//loginId 규칙: 영어 + 숫자 13자 미만
		
		@NotBlank
		private String pass;//loginId 규칙: 영어 + 특수문자 + 숫자 13자 미만
		
		@NotBlank
		private String username;
		
		@Email
		private String email;
		
		@NotBlank
		private String hpNum;
		
		@Enumerated(EnumType.STRING)
		private Set<AccountRole> role;
		
		@Enumerated(EnumType.STRING)
		private Set<AccountPart> part;
}

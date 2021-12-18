package com.mermer.cm.entity.dto;

import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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

		@NonNull
		private int roleCd;
		
		@NotBlank
		private String username;
		
		@Email
		private String email;
		
		@NotBlank
		private String hpNum;
		
		@Enumerated(EnumType.STRING)
		private Set<AccountRole> accountRole;
		
		@Enumerated(EnumType.STRING)
		private Set<AccountPart> accountPart;
}

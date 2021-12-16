package com.mermer.cm.entity.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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
		
		@NonNull
		private String username;
		
		@NonNull
		private String email;
		
		@NonNull
		private String hpNum;
		
		@Enumerated(EnumType.STRING)
		private AccountRole accountRole;
		
		@Enumerated(EnumType.STRING)
		private AccountPart accountPart;
}

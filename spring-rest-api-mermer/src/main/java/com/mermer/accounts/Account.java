package com.mermer.accounts;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Account {
	@Id @GeneratedValue
	private Integer id;
	
	private String email;
	
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)// 자주가져오고, Role 데이터가 많지 않기 때문에 EAGER 모드로 패치(set, list의 기본은 LAZY 패치)
	@Enumerated(EnumType.STRING)
	private Set<AccountRole> roles;
	
}

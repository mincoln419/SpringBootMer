package com.mermer.cm.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountRole {

	ADMIN("운영자"),
	GOLD("골드"),
	SLVER("실버"),
	GUEST("손님"),
	USER("신규");
	
	private final String description;
}

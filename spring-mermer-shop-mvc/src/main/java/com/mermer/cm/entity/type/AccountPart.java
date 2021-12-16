package com.mermer.cm.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountPart {
	
	BULLETIN("게시판"),
	NOTION("공지사항"),
	PHOTO("사진");
	
	private final String description;
}

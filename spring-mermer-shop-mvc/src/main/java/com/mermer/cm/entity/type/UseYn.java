package com.mermer.cm.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @packageName : com.mermer.cm.entity.type
 * @fileName : UseYn.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description : 사용여부 enumeration
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성
 */
@AllArgsConstructor
@Getter
public enum UseYn {
	Y("사용"),
	N("미사용");
	
	private final String description;
}

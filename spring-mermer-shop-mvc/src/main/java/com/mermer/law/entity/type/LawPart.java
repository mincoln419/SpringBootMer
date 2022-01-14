
package com.mermer.law.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @packageName : com.mermer.law.entity.type
 * @fileName : LawPart.java 
 * @author : Mermer 
 * @date : 2022.01.14 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.14 Mermer 최초 생성
 */
@Getter
@AllArgsConstructor
public enum LawPart {

	CRIMIANL("형사"),
	CIVIL("민사");
	
	private final String description;
}

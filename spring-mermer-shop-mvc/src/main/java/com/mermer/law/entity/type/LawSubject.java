/**
 * @packageName : com.mermer.law.entity.type
 * @fileName : LawSubject.java 
 * @author : Mermer 
 * @date : 2022.01.13 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.13 Mermer 최초 생성
 */
package com.mermer.law.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LawSubject {
	ADULT("성인"),
	IMMATURE("청소년"),
	CHILDREN("어린이"),
	MAN("남성"),
	WOMAN("여성"),
	STUFF("물건"),
	ANIMAL("동물");
	
	private final String description;
}

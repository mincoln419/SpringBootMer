
package com.mermer.law.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @packageName : com.mermer.law.entity.type
 * @fileName : LawMethod.java 
 * @author : Mermer 
 * @date : 2022.01.13 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.13 Mermer 최초 생성
 */
@Getter
@AllArgsConstructor
public enum LawMethod {
	

	ONDO("작위"),
	NOTDO("부작위"),
	DANGER("위함한 수단"),
	MULTIPLE("다수의 행위"),
	COOPERATION("공동정범"),
	AID("방조범"),
	FELONY("교사범");
		
	
	private final String description;
}


package com.mermer.law.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @packageName : com.mermer.law.entity.type
 * @fileName : LawResult.java 
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
public enum LawResult {

	DEAD("사망"),
	HURT("상해"),
	LOSS("재산상손해"),
	PREJUDICE("자유침해"),
	HONOR_PREJUDICE("명예침해"),
	SEXUAL_PREJUDICE("성적침해");	
	
		
	private final String description;
}

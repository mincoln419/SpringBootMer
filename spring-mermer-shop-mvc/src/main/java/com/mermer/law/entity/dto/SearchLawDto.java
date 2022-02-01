
package com.mermer.law.entity.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.mermer.law.entity.type.LawPart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @packageName : com.mermer.law.entity.dto
 * @fileName : LawDto.java 
 * @author : Mermer 
 * @date : 2022.01.11 
 * @description : 법률 정보 요청시에 사용하는 기본 항목에 dto - validation하기 위함 -> 공개법률정보 시스템 api 호출하기전에 validation하기 위함
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.11 Mermer 최초 생성
 */
@Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SearchLawDto {
	
	@Enumerated(EnumType.STRING)
	private LawPart lawPart;
	
	private String articleNum;
	
	private String articleTitle;
	
	private String searchBody;
		

}

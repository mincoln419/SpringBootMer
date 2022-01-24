
package com.mermer.mermerbatch.core.entity.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * @packageName : com.mermer.mermerbatch.core.entity
 * @fileName : LawSearchDto.java 
 * @author : Mermer 
 * @date : 2022.01.17 
 * @description : 본 검색전 페이지와 총수량에 따라 API를 얼마나 호출해야 하는지 계산하기 위한 DTO
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.17 Mermer 최초 생성
 */
@ToString
@Getter
@XmlRootElement(name = "LawSearch")
@AllArgsConstructor @NoArgsConstructor
public class LawSearchDto {

	@XmlElement(name ="page")
	private String page;
	
	@XmlElement(name ="totalCnt")
	private String total;
	
	@XmlElement(name ="numOfRows")
	private String rows;
}

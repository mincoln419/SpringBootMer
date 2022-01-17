/**
 * @packageName : com.mermer.mermerbatch.core.entity.dto
 * @fileName : BasicDto.java 
 * @author : Mermer 
 * @date : 2022.01.18 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.18 Mermer 최초 생성
 */
package com.mermer.mermerbatch.core.entity.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@XmlRootElement(name = "기본정보")
@AllArgsConstructor @NoArgsConstructor
public class BasicDto {
	
	@XmlElement(name ="법령ID")
	private String lawId;
	
	@XmlElement(name ="법종구분")
	private String lawType;
	
	@XmlElement(name ="법령명_한글")
	private String lawName;
}

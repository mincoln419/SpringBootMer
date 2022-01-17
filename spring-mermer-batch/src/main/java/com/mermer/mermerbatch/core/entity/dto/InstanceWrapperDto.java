
package com.mermer.mermerbatch.core.entity.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @packageName : com.mermer.mermerbatch.core.entity.dto
 * @fileName : InstanceWrapperDto.java 
 * @author : Mermer 
 * @date : 2022.01.17 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.17 Mermer 최초 생성
 */
@ToString
@Getter
@XmlRootElement(name = "법령")
@AllArgsConstructor @NoArgsConstructor
public class InstanceWrapperDto {

	@XmlElement(name ="기본정보")
	private BasicDto basicDto;
	
	@XmlElement(name ="조문")
	private ArticleWrapperDto articleWrapperDto;
}

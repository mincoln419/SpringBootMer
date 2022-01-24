
package com.mermer.mermerbatch.core.entity.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * @packageName : com.mermer.mermerbatch.core.entity.dto
 * @fileName : HoArticleDto.java 
 * @author : Mermer 
 * @date : 2022.01.17 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.17 Mermer 최초 생성
 */
@ToString
@XmlRootElement(name = "호")
@AllArgsConstructor @NoArgsConstructor
@Getter
public class HoArticleDto {

	@XmlElement(name ="호번호")
	private String hoArticleNum;
	
	@XmlElement(name ="호내용")
	private String hoArticleContent;

	public String getHoArticleContent() {
		return hoArticleContent;
	}

	/**
	 * @return
	 */
	public Integer calArticleNum() {
		return getHoArticleNum().charAt(0) - 9311;
	}
	
	
}

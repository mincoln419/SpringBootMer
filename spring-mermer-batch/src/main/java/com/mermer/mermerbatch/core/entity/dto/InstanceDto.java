
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
 * @fileName : InstanceDto.java 
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
@XmlRootElement(name = "조문단위")
@AllArgsConstructor @NoArgsConstructor
public class InstanceDto {
	
	@XmlElement(name ="조문번호")
	private String articleNum;
	
	@XmlElement(name ="조문여부")
	private String articleType;
	
	@XmlElement(name ="조문제목")
	private String articleTitle;
	
	@XmlElement(name ="조문내용")
	private String articleContent;
	
	@XmlElement(name ="항")
	private List<SubArticleDto> subArticle;
	
}

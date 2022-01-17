
package com.mermer.mermerbatch.core.entity.dto;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @packageName : com.mermer.mermerbatch.core.entity.dto
 * @fileName : ArticleDto.java 
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
@XmlRootElement(name = "law")
@AllArgsConstructor @NoArgsConstructor
public class ArticleDto {

	private String name;
}

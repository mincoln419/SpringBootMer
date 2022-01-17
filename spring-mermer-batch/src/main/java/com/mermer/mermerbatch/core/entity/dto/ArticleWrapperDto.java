
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
 * @fileName : ArticleWrapperDto.java 
 * @author : Mermer 
 * @date : 2022.01.18 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.18 Mermer 최초 생성
 */
@ToString
@Getter
@XmlRootElement(name = "조문")
@AllArgsConstructor @NoArgsConstructor
public class ArticleWrapperDto {

	@XmlElement(name ="조문단위")
	private List<InstanceDto> instanceDto;
}

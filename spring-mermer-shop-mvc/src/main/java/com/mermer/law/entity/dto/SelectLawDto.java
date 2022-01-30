
package com.mermer.law.entity.dto;

import java.time.LocalDateTime;

import com.mermer.law.entity.LawDomain;
import com.mermer.law.entity.embeded.Structure;
import com.mermer.law.entity.type.LawPart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @packageName : com.mermer.law.entity.dto
 * @fileName : SelectLawDto.java 
 * @author : Mermer 
 * @date : 2022.01.30 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.30 Mermer 최초 생성
 */
@Getter
@RequiredArgsConstructor
public class SelectLawDto {

	private final Long id;
	
	private final Integer articleNum;
	
	private final String purnishment;
	
	private final LawDomain lawDomain;
	
	
	
}

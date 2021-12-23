
package com.mermer.cm.entity.dto;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.serializer.AccountSerializer;
import com.mermer.cm.entity.type.AccountPart;
import com.mermer.cm.entity.type.AccountRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @packageName : com.mermer.cm.entity.dto
 * @fileName : NoticeDto.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description : Notice를 validation 하기 위한 Dto
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성. notice는 제목과 내용만 입력받고 나머지는 auto-complement
 */
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class NoticeDto {

	@NotBlank
	private String title;
	
	@Lob
	@NotBlank
	private String content;
	
	private String writerIp;
	
}

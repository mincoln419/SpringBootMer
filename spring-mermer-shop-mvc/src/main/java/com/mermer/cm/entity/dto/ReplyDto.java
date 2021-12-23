
package com.mermer.cm.entity.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.mermer.cm.entity.Reply;
import com.mermer.cm.entity.type.AccountPart;
import com.mermer.cm.entity.type.AccountRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @packageName : com.mermer.cm.entity.dto
 * @fileName : ReplyDto.java 
 * @author : Mermer 
 * @date : 2021.12.23 
 * @description : 답글 입력시 validation 위한 Dto
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.23 Mermer 최초 생성
 */
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ReplyDto {
	
	@NotBlank
	private String content;
	
	private String writerIp;//Controller에서 자동세팅

}

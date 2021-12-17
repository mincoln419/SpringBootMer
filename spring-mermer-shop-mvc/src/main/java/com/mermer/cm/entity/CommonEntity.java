
package com.mermer.cm.entity;

import java.time.LocalDateTime;

import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mermer.cm.entity.serializer.AccountSerializer;

/**
 * @packageName : com.mermer.cm.entity
 * @fileName : CommonEntity.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description : Entity 생성시 공통적으로 사용되는 필드를 미리 만들어놓음
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성
 */
public class CommonEntity {
	@CreatedDate
	private LocalDateTime instDtm;//생성일시
	
	@LastModifiedDate
	private LocalDateTime mdfDtm;//수정일시
	
	//단방향(Account -> Notice)으로 참조하도록 매핑
	@ManyToOne
	@JsonSerialize(using = AccountSerializer.class)
	private Account instId;//생성자ID 
	
	@ManyToOne
	@JsonSerialize(using = AccountSerializer.class)
	private Account mdfId;//수정자ID
	
	private String useYn; //사용여부
}

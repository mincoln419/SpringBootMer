
package com.mermer.cm.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mermer.cm.entity.serializer.AccountSerializer;
import com.mermer.cm.entity.type.UseYn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@SuperBuilder
public class CommonEntity {
	
	@CreatedDate
	protected LocalDateTime instDtm;//생성일시
	
	@LastModifiedDate
	protected LocalDateTime mdfDtm;//수정일시
	
	//단방향(Account -> Notice)으로 참조하도록 매핑
	@ManyToOne
	@JsonSerialize(using = AccountSerializer.class)
	protected Account instId;//생성자ID
	
	@ManyToOne
	@JsonSerialize(using = AccountSerializer.class)
	protected Account mdfId;//수정자ID
	
	@Enumerated(EnumType.STRING)
	@Builder.Default
	protected UseYn useYn = UseYn.Y; //사용여부(default 값 Y);
}

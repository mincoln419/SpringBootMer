
package com.mermer.cm.entity.embeded;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.serializer.AccountSerializer;
import com.mermer.cm.entity.type.UseYn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
/**
 * @packageName : com.mermer.cm.entity.embeded
 * @fileName : CommonEmbeded.java 
 * @author : Mermer 
 * @date : 2022.01.10 
 * @description : 엔티티 생성에 공통적으로 들어가는 컬럼 값 
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.10 Mermer 최초 생성
 */
@MappedSuperclass
@Getter @Setter
@SuperBuilder
@NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class) // 이걸집어넣어줘야 instDtm, mdfDtm  자동으로 세팅해줌
public class CommonEmbeded {

	
	/* Entity 공통부 */
	@CreatedDate
	protected LocalDateTime instDtm;//생성일시
	
	@LastModifiedDate
	protected LocalDateTime mdfDtm;//수정일시
	
	@ManyToOne
	@JsonSerialize(using = AccountSerializer.class)
	protected Account inster;//생성자ID
	
	@ManyToOne
	@JsonSerialize(using = AccountSerializer.class)
	protected Account mdfer;//수정자ID
	
	@Enumerated(EnumType.STRING)
	@Builder.Default
	protected UseYn useYn = UseYn.Y; //사용여부(default 값 Y);
	
}

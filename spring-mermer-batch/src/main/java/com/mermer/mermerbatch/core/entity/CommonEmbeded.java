
package com.mermer.mermerbatch.core.entity;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mermer.mermerbatch.core.entity.type.UseYn;

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
public abstract class CommonEmbeded {

	
	/* Entity 공통부 */
	@CreatedDate
	protected LocalDateTime instDtm;//생성일시
	
	@LastModifiedDate
	protected LocalDateTime mdfDtm;//수정일시
	
	protected Long inster;//생성자ID
	
	//lazy(지연로딩)로 하면 proxy 단계에서 account는 처음에는 조인해서 조회하지 않음, 
	/* 실무에서 예상치 못한 SQL 나가는 걸 방지하기 위해 lazy를 쓰는 게 좋다 -> 한번에 가져오고싶으면 jpql 작업시 fetch 조인으로 처리
	 * JPQL에서 문제가 많기 때문에 lazy를 써야 한다. - jpql -> sql을 번역 -> 즉시로딩임을 확인 후 외부참조 된 테이블을 안가져오면 다시 transaction.. 
	 * ... n + 1 문제라고 한다(n개의 쿼리가 추가로 나간다는의미)
	 */
	//나중에 account에 접근이 있을 경우에 account 테이블에 접근 cf. eager(즉시로딩)로 하면 항상 account를 조회함
	
	protected Long mdfer;//생성자ID
	
	@Enumerated(EnumType.STRING)
	@Builder.Default
	protected UseYn useYn = UseYn.Y; //사용여부(default 값 Y);
	
}


package com.mermer.mermerbatch.core.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @packageName : com.mermer.mermerbatch.core.entity
 * @fileName : PageWork.java 
 * @author : Mermer 
 * @date : 2022.01.17 
 * @description : 페이지 작업을 위한 Entity
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.17 Mermer 최초 생성
 */
@Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "TB_CM_PAGE_TEMP")
@SuperBuilder @Getter @Setter @EqualsAndHashCode(of = "id", callSuper = false)
@SequenceGenerator(name = "SQ_PAGE_WORK_ID_GENERATOR", sequenceName = "SQ_PAGE_WORK_ID", initialValue = 1, allocationSize = 1)
public class PageWork extends CommonEmbeded{
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PAGE_WORK_ID_GENERATOR")
	private Long id;


	/* 검색조건이 동일한 경우인지 체크하기 위함 */
	private String search;
	private String query;
	
	//작업을 마쳤는지 체크하기 위함 -> 배치 작업이 끝나면 true
	private boolean finished;
	
	//전체 건수
	private Long total;
	
	//페이지당 최대건수
	private Long rows;
	
	//페이지 수
	private Long page;
	
	//처리해야 할 건수
	private Long targetCnt;
	
	//현재 페이지 정보를 가지고 처리해야 할 건수를 계산함
	public void updateTargetCnt() {
		Long cnt = Long.divideUnsigned(getTotal(), getRows());
		
		if(Long.compare(0, Long.remainderUnsigned(getTotal(), getRows())) < 0) cnt++;
		
		setTargetCnt(cnt);
		
	}
	
}

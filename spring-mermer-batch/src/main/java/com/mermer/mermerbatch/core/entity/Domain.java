/**
 * @packageName : com.mermer.mermerbatch.core.entity
 * @fileName : Domain.java 
 * @author : Mermer 
 * @date : 2022.01.14 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.14 Mermer 최초 생성
 */
package com.mermer.mermerbatch.core.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "TB_LW_DOMAIN")
@SuperBuilder @Getter @Setter @EqualsAndHashCode(of = "id", callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "SQ_LW_DOMAIN_ID_GENERATOR", sequenceName = "SQ_LW_DOMAIN_ID", initialValue = 1, allocationSize = 1)
public class Domain extends CommonEmbeded{

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_LW_DOMAIN_ID_GENERATOR")
	private Long id;
	
	private Integer lawId;
	
	private Integer lawMST;//공개법률정보에서 사용하는 법률코드

	private String lawName;//DB에 저장시킬 법률명
	
}

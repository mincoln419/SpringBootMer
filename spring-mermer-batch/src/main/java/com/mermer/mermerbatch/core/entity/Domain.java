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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "LW_TB_DOMAIN")
@SuperBuilder @Getter @Setter @EqualsAndHashCode(of = "id", callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "LW_SQ_DOMAIN_ID_GENERATOR", sequenceName = "LW_SQ_DOMAIN_ID", initialValue = 1, allocationSize = 1)
public class Domain extends CommonEmbeded{

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LW_SQ_DOMAIN_ID_GENERATOR")
	@Column(name = "DOMAIN_ID")
	private Long id;
	
	
	private String lawDomainName;//DB에 저장시킬 법률명
	
	private String lawDomainCode;//공개법률정보에서 사용하는 법률코드
	
	@Column(name = "LAW_CONTENT")
	@Lob
	private String content;
	
	
}

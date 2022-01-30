/**
 * @packageName : com.mermer.law.entity
 * @fileName : LawInstance.java 
 * @author : Mermer 
 * @date : 2022.01.12 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.12 Mermer 최초 생성
 */
package com.mermer.law.entity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mermer.cm.entity.embeded.CommonEmbeded;
import com.mermer.law.entity.embeded.Structure;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity @NoArgsConstructor @AllArgsConstructor
@SuperBuilder @Getter @Setter @EqualsAndHashCode(of = "id", callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "SQ_LW_INSTANCE_ID_GENERATOR", sequenceName = "SQ_LW_ID_GENERATOR", initialValue = 1, allocationSize = 1)
@Inheritance(strategy = InheritanceType.JOINED)//상속전략 
@DiscriminatorColumn(name = "LAW_TYPE")
@Table(name = "TB_LW_INSTANCE")
public abstract class LawInstance extends CommonEmbeded{
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_LW_INSTANCE_ID_GENERATOR")
	protected Long id;
	
	@ManyToOne
	@JoinColumn(name = "DOMAIN_ID")
	protected LawDomain domain;
	
	protected Integer articleNum;
	
	@Embedded
	protected Structure structure;
	
	
}
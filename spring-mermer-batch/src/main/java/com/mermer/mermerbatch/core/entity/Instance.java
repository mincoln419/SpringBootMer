/**
 * @packageName : com.mermer.mermerbatch.core.entity
 * @fileName : Instance.java 
 * @author : Mermer 
 * @date : 2022.01.17 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.17 Mermer 최초 생성
 */
package com.mermer.mermerbatch.core.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mermer.mermerbatch.core.entity.embeded.Article;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "TB_LW_INSTANCE")
@SuperBuilder @Getter @Setter @EqualsAndHashCode(of = "id", callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "SQ_LW_INSTANCE_ID_GENERATOR", sequenceName = "SQ_LW_INSTANCE_ID", initialValue = 1, allocationSize = 1)
public class Instance extends CommonEmbeded{

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_LW_INSTANCE_ID_GENERATOR")
	@Column(name = "INSTANCE_ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Domain domain;
	
	@Embedded
	private Article article;

	
}

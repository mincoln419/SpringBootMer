
package com.mermer.law.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mermer.cm.entity.embeded.CommonEmbeded;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @packageName : com.mermer.law.entity
 * @fileName : LawInstance.java 
 * @author : Mermer 
 * @date : 2022.01.11 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.11 Mermer 최초 생성
 */
@Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "LW_TB_INSTANCE")
@SuperBuilder @Getter @Setter @EqualsAndHashCode(of = "id", callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "LW_SQ_INSTANCE_ID_GENERATOR", sequenceName = "LW_SQ_ID_GENERATOR", initialValue = 1, allocationSize = 1)
public class LawInstance extends CommonEmbeded{
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LW_SQ_INSTANCE_ID_GENERATOR")
	private Long id;
	
	
	
}

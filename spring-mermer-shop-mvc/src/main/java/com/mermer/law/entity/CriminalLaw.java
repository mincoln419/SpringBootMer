
package com.mermer.law.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mermer.cm.entity.embeded.CommonEmbeded;
import com.mermer.law.entity.embeded.Structure;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @packageName : com.mermer.law.entity
 * @fileName : CriminalLaw.java 
 * @author : Mermer 
 * @date : 2022.01.12 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.12 Mermer 최초 생성
 */
@Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "LW_TB_CRIMINAL")
@SuperBuilder @Getter @Setter
@DiscriminatorValue("CIVIL")
public class CriminalLaw extends LawInstance{

	
	@Embedded
	private Structure structure;
	
	@NotBlank
	private String purnishment;
	
}

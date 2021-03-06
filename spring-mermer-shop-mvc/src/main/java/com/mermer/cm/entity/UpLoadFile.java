
package com.mermer.cm.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mermer.cm.entity.embeded.CommonEmbeded;
import com.mermer.cm.entity.serializer.AccountSerializer;
import com.mermer.cm.entity.type.AccountPart;
import com.mermer.cm.entity.type.AccountRole;
import com.mermer.cm.entity.type.UseYn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @packageName : com.mermer.cm.entity
 * @fileName : UpLoadFile.java 
 * @author : Mermer 
 * @date : 2022.01.06 
 * @description : 파일업로드를 위한 Entity
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.06 Mermer 최초 생성
 */
@Entity @NoArgsConstructor @AllArgsConstructor
@SuperBuilder @Getter @Setter @EqualsAndHashCode(of = "fileId", callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "FILE_ID_GENERATOR", sequenceName = "FILE_GENERATOR", initialValue = 1, allocationSize = 1)
public class UpLoadFile extends CommonEmbeded {

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_ID_GENERATOR")
	Long fileId;
	
	String name;
	
	@NotBlank
	String path;
	
	@NotBlank
	String fileName;
	

}

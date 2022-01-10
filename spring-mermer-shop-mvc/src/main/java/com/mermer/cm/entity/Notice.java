
package com.mermer.cm.entity;

import java.sql.Clob;
import java.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mermer.cm.entity.embeded.CommonEmbeded;
import com.mermer.cm.entity.serializer.AccountSerializer;
import com.mermer.cm.entity.type.UseYn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @packageName : com.mermer.cm.entity
 * @fileName : Notice.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description : 공지사항 게시판 만들기
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성
 */

@Entity 
@SuperBuilder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@SequenceGenerator(name = "NOTICE_ID_GENERATOR", sequenceName = "NOTICE_GENERATOR", initialValue = 1, allocationSize = 1)
public class Notice extends CommonEmbeded {

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTICE_ID_GENERATOR")
	private Long id;

	private String title;
	
	@Lob
	private String content;
	
	@Builder.Default
	private Integer readCnt = 0;
	
	private String writerIp;
	
	/* *
	 * 공지사항을 읽은 횟수 갱신
	 * */
	public void updateReadCnt() {
		this.readCnt++;
	}
	
	
}

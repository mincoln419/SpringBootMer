
package com.mermer.cm.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mermer.cm.entity.embeded.CommonEmbeded;
import com.mermer.cm.entity.serializer.AccountSerializer;
import com.mermer.cm.entity.serializer.NoticeSerializer;
import com.mermer.cm.entity.serializer.ReplySerializer;
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
 * @fileName : Reply.java 
 * @author : Mermer 
 * @date : 2021.12.23 
 * @description : 공지사항, 자유게시판 댓글 entity
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.23 Mermer 최초 생성
 */
@Entity
@SuperBuilder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EntityListeners(AuditingEntityListener.class) // 이걸집어넣어줘야 instDtm, mdfDtm  자동으로 세팅해줌
@EqualsAndHashCode(of = "id", callSuper = false)
@SequenceGenerator(name = "REPLY_ID_GENERATOR", sequenceName = "REPLY_GENERATOR", initialValue = 1, allocationSize = 1)
public class Reply extends CommonEmbeded{
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPLY_ID_GENERATOR")
	private Long id;
	
	private String content;
	
	@ManyToOne
	@JsonSerialize(using = ReplySerializer.class)
	private Reply parent;
	
	@ManyToOne
	@JsonSerialize(using = NoticeSerializer.class)
	private Notice notice;
	
	
	private String writerIp;
	//단방향(Account -> Notice)으로 참조하도록 매핑

}


package com.mermer.cm.entity;

import java.sql.Clob;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EntityListeners(AuditingEntityListener.class) // 이걸집어넣어줘야 instDtm, mdfDtm  자동으로 세팅해줌
@EqualsAndHashCode(of = "id")
public class Notice {

	@Id @GeneratedValue
	private Long id;

	private String title;
	
	@Lob
	private String content;
	
	@Builder.Default
	private Integer readCnt = 0;
	
	private String writerIp;
	
	/* Entity 공통부 */
	@CreatedDate
	protected LocalDateTime instDtm;//생성일시
	
	@LastModifiedDate
	protected LocalDateTime mdfDtm;//수정일시
	
	//단방향(Account -> Notice)으로 참조하도록 매핑
	@ManyToOne
	@JsonSerialize(using = AccountSerializer.class)
	protected Account inster;//생성자ID
	
	@ManyToOne
	@JsonSerialize(using = AccountSerializer.class)
	protected Account mdfer;//수정자ID
	
	@Enumerated(EnumType.STRING)
	@Builder.Default
	protected UseYn useYn = UseYn.Y; //사용여부(default 값 Y);
	
	
	/* *
	 * 공지사항을 읽은 횟수 갱신
	 * */
	public void updateReadCnt() {
		this.readCnt++;
	}
	
	
}

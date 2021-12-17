
package com.mermer.cm.entity;

import java.sql.Clob;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
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
@Entity @NoArgsConstructor @AllArgsConstructor
@SuperBuilder @Getter @Setter @EqualsAndHashCode(of = "noiceId")
@EntityListeners(AuditingEntityListener.class) // 이걸 집어넣어줘야 instDtm, mdfDtm  자동으로 세팅해줌
public class Notice extends CommonEntity{

	@Id @GeneratedValue
	private Long noiceId;

	private String title;
	
	@Lob
	private String content;
	
	private Integer readCnt;
	
	private String wirterIp;
	
	 
	
	
}

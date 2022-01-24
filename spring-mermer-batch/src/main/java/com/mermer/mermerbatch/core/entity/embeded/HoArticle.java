
package com.mermer.mermerbatch.core.entity.embeded;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @packageName : com.mermer.mermerbatch.core.entity.embeded
 * @fileName : HoArticle.java 
 * @author : Mermer 
 * @date : 2022.01.17 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.17 Mermer 최초 생성
 */
@Getter //setter를 없애고 생성자만 만들수 있게 해서 불변객체로 만든다 -> 기존 값의 경우에도 새로 만들어야 함
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
@Table(name = "TB_LW_HO_ARTICLE")
@SequenceGenerator(name = "SQ_LW_HO_ARTICLE_ID_GENERATOR", sequenceName = "SQ_LW_HO_ARTICLE_ID", initialValue = 1, allocationSize = 1)
public class HoArticle {

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_LW_HO_ARTICLE_ID_GENERATOR")
	@Column(name = "HO_ARTICLE_ID")
	private Long id;
	
	private Integer subArticleNum;//항
	
	private Integer hoArticleNum;//호
	
	private String content;//조항 내용
}

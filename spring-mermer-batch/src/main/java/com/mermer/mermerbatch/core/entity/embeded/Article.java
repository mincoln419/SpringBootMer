
package com.mermer.mermerbatch.core.entity.embeded;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;

import com.mermer.mermerbatch.core.entity.Instance;
import com.mermer.mermerbatch.core.entity.type.ArticleType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @packageName : com.mermer.mermerbatch.core.entity.embeded
 * @fileName : Article.java 
 * @author : Mermer 
 * @date : 2022.01.17 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.17 Mermer 최초 생성
 */
@Embeddable
@Getter //setter를 없애고 생성자만 만들수 있게 해서 불변객체로 만든다 -> 기존 값의 경우에도 새로 만들어야 함
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Article {
	
	private Integer articleNum;//조
	
	@Enumerated(EnumType.STRING)
	private ArticleType articleType;//전문,조문 구분  
	
	private String content;//조문 내용
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "INSTANCE_ID")
	@Builder.Default
	private List<SubArticle> subArticleList = new ArrayList<>();//항
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "INSTANCE_ID")
	@Builder.Default
	private List<HoArticle> hoArticleList = new ArrayList<>();//호
	
}

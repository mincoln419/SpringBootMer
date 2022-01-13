
package com.mermer.law.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.embeded.CommonEmbeded;
import com.mermer.cm.entity.type.AccountPart;
import com.mermer.cm.entity.type.AccountRole;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @packageName : com.mermer.law.entity
 * @fileName : LawDomain.java 
 * @author : Mermer 
 * @date : 2022.01.11 
 * @description : 법률 도메인 엔티티
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.11 Mermer 최초 생성
 */


@Entity @NoArgsConstructor @AllArgsConstructor
@Table(name = "LW_TB_DOMAIN")
@SuperBuilder @Getter @Setter @EqualsAndHashCode(of = "id", callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "LW_SQ_DOMAIN_ID_GENERATOR", sequenceName = "LW_SQ_DOMAIN_ID", initialValue = 1, allocationSize = 1)
public class LawDomain extends CommonEmbeded{

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LW_SQ_DOMAIN_ID_GENERATOR")
	@Column(name = "DOMAIN_ID")
	private Long id;
	
	@NotBlank
	private String lawDomainName;//DB에 저장시킬 법률명
	@NotBlank
	private String lawDomainCode;//공개법률정보에서 사용하는 법률코드
	
	@Column(name = "LAW_CONTENT")
	@Lob
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")// 자기 참조 -- 일반법/특별법 구분 위함
	private LawDomain parent;
	
}

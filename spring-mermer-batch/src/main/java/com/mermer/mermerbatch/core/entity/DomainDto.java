
package com.mermer.mermerbatch.core.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @packageName : com.mermer.mermerbatch.core.entity
 * @fileName : DomainDto.java 
 * @author : Mermer 
 * @date : 2022.01.16 
 * @description : 법령 목록 검색 api 데이터 수신에서 사용할 DTO
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.16 Mermer 최초 생성
 */
@ToString
@Setter @Getter
@XmlRootElement(name = "law")
@AllArgsConstructor @NoArgsConstructor
public class DomainDto {
	
	@XmlElement(name ="법령일련번호")
	private String lawSerial; //법령일련번호
	
	@XmlElement(name ="현행연혁코드")
	private String isCurrent;//현행연혁코드 (현행/폐기)
	
	@XmlElement(name ="법령명한글")
	private String lawName; // 법령명한글
	
	@XmlElement(name ="법령약칭명")
	private String shortName;//법령약칭명
	
	@XmlElement(name ="법령ID")
	private String lawId;//법령ID
	
	@XmlElement(name ="공포일자")
	private String proClamDate;//공포일자
	
	@XmlElement(name ="공포번호")
	private String proClamNum;//공포번호
	
	@XmlElement(name ="제개정구분명")
	private String changeType;//제개정구분명
	
	@XmlElement(name ="소관부처코드")
	private String departNum;//소관부처코드
	
	@XmlElement(name ="소관부처명")
	private String departName;//소관부처명
	
	@XmlElement(name ="법령구분명")
	private String lawType;//법령구분명
	
	@XmlElement(name ="자법타법여부")
	private String passive;//자법타법여부
	
	@XmlElement(name ="시행일자")
	private String enforceDate;//시행일자
	
	@XmlElement(name ="법령상세링크")
	private String detailLink;//법령상세링크
}

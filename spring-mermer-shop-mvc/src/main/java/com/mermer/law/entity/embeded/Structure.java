/**
 * @packageName : com.mermer.law.entity.embeded
 * @fileName : Structure.java 
 * @author : Mermer 
 * @date : 2022.01.13 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.13 Mermer 최초 생성
 */
package com.mermer.law.entity.embeded;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;

import com.mermer.law.entity.type.LawMethod;
import com.mermer.law.entity.type.LawResult;
import com.mermer.law.entity.type.LawSubject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter //setter를 없애고 생성자만 만들수 있게 해서 불변객체로 만든다 -> 기존 값의 경우에도 새로 만들어야 함
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Structure {
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<LawSubject> subject; // 주체
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<LawSubject> object; // 객체
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<LawMethod> method; // 수단, 방법
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<LawResult> result; // 결과
	
	
	

}

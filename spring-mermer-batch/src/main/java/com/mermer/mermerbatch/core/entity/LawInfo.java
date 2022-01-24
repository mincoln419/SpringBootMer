
package com.mermer.mermerbatch.core.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
/**
 * @packageName : com.mermer.mermerbatch.core.entity
 * @fileName : LawIdList.java 
 * @author : Mermer 
 * @date : 2022.01.24 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.24 Mermer 최초 생성
 */
@Getter
@RequiredArgsConstructor
public class LawInfo {

	private Integer lawId;
	
	private Integer lawMst;//공개법률정보에서 사용하는 법률코드

	private String lawName;//DB에 저장시킬 법률명
}

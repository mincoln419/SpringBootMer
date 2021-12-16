
package com.mermer.cm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @packageName : com.mermer.cm.exception
 * @fileName : BizExceptionCode.java 
 * @author : Mermer 
 * @date : 2021.12.16 
 * @description : 오류코드 ENUMERATION
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.16 Mermer 최초 생성
 */
@Getter
@AllArgsConstructor
public enum BizExceptionCode {

	DATE_MACH_ERR("시작 시간이 종료시간 보다 앞섭니다"),
	DUPLICATED_USER_ID("중복되는 유저ID가 있습니다"),
	
	INTERNAL_SERVER_ERROR("서버에 오류가 발생하였습니다"),
	INVALID_REQUEST("잘못된 요청입니다")
	
	;
	
	
	private final String message;
}

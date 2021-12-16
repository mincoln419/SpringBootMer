
package com.mermer.cm.validator;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.mermer.cm.entity.dto.AccountDto;
import com.mermer.cm.exception.BizException;
import com.mermer.cm.exception.BizExceptionCode;

/**
 * @packageName : com.mermer.cm.validator
 * @fileName : AccountValidator.java 
 * @author : Mermer 
 * @date : 2021.12.16 
 * @description : 계정정보 validator
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.16 Mermer 최초 생성
 */
@Component
public class AccountValidator {


	/**
	 * @param accountDto
	 * @param errors
	 */
	public void accountValidate(AccountDto accountDto, Errors errors) {
		/*
		 * @description:전화번호 유효성 검사 		 
		 * */
		if (!accountDto.getHpNum().startsWith("01")||accountDto.getHpNum().length() < 10) {
			errors.rejectValue("hpNum", "휴대폰 오류");//글로벌 에러 - 여러 에러 사유가 조합되어있을 경우..
			//BizExceptionCode.PHONE_NUMBER.getMessage()
		}

	}
}

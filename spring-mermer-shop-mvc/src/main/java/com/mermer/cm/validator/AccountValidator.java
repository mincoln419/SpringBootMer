
package com.mermer.cm.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.dto.AccountDto;
import com.mermer.cm.entity.type.AccountRole;
import com.mermer.cm.repository.AccountRepository;

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

	
	@Autowired
	AccountRepository accountRepository;

	/**
	 * @param accountDto
	 * @param errors
	 */
	public void validate(AccountDto accountDto, Errors errors) {
		
		
		/*
		 * @description:전화번호 유효성 검사 		 
		 * */
		if (!accountDto.getHpNum().startsWith("01")||accountDto.getHpNum().length() < 10) {
			errors.rejectValue("hpNum", "휴대폰 오류");//글로벌 에러 - 여러 에러 사유가 조합되어있을 경우..
			//BizExceptionCode.PHONE_NUMBER.getMessage()
		}

	}

	
	public void accountValidate(AccountDto accountDto, Errors errors) {		
		
		/*
		 * @description 로그인ID 고유성 검사
		 *  		 
		 * */
		if (!accountRepository.findByLogin(accountDto.getLogin()).isEmpty()) {
			errors.rejectValue("login", "로그인 중복");
		}

	}
	
	
	
	/**
	 * @method validateAccount
	 * @param account
	 * @return
	 * boolean
	 * @description 
	 */
	public boolean validateAccount(Account account, Errors errors) {
		
		if(!account.getRole().contains(AccountRole.ADMIN)) {
			//errors.rejectValue("accountRole", "관리자 권한이 필요합니다");
			return true;
		}
		
		return false;
	}
}

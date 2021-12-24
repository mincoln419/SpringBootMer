
package com.mermer.cm.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.dto.NoticeDto;
import com.mermer.cm.entity.dto.ReplyDto;

/**
 * @packageName : com.mermer.cm.validator
 * @fileName : NoticeValidator.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description : NoticeDto의 validator
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성
 */
@Component
public class NoticeValidator {

	/**
	 * @methond noticeValidate
	 * @param noticeDto
	 * @param errors
	 * void
	 */
	public void noticeValidate(NoticeDto noticeDto, Account account, Errors errors) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @method replyValidation
	 * @param replyDto
	 * @param errors
	 * void
	 * @description 
	 */
	public void replyValidation(ReplyDto replyDto, Account account, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}

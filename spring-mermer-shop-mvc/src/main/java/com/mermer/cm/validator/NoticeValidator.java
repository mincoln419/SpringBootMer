
package com.mermer.cm.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.Reply;
import com.mermer.cm.entity.dto.NoticeDto;
import com.mermer.cm.entity.dto.ReplyDto;
import com.mermer.cm.repository.NoticeReplyRepository;

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
	
	@Autowired
	private NoticeReplyRepository noticeReplyRepository;

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
	public void replyValidation(Long replyId, Account account, Errors errors) {

		Optional<Reply> result = noticeReplyRepository.findById(replyId);
		
		if(result.isEmpty()) {
			errors.reject("NotFound","수정할 수 있는 ");
		}else if(result.get().getInster().getId().compareTo(account.getId()) !=0) {
			errors.reject("UnAuthorized","수정 권한이 없습니다");
		}
		
	}

}

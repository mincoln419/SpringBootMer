
package com.mermer.law.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.dto.NoticeDto;
import com.mermer.cm.util.CurrentUser;
import com.mermer.law.entity.dto.LawDto;

/**
 * @packageName : com.mermer.law.entity
 * @fileName : LawAPIController.java 
 * @author : Mermer 
 * @date : 2022.01.11 
 * @description : 공개법률정보 시스템에서 API로 데이터를 받아오는 Controller - 데이터를 호출하여 API서버에 저장시키기 위한 Controller -> create만 가능
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.11 Mermer 최초 생성
 */
@RestController
public class LawAPIController {

	
	@PostMapping
	public ResponseEntity callLawInfo(HttpServletRequest req,
			   @RequestBody @Validated LawDto lawDto,
			   Errors errors,
			   @CurrentUser Account account
			) {
		
		return null;
	}
	
	
}

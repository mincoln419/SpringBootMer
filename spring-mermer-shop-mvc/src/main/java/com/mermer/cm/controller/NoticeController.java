
package com.mermer.cm.controller;

import static com.mermer.cm.exception.ErrorsResource.badRequest;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mermer.cm.entity.dto.NoticeDto;
import com.mermer.cm.repository.NoticeRepository;
import com.mermer.cm.service.NoticeService;
import com.mermer.cm.validator.NoticeValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @packageName : com.mermer.cm.controller
 * @fileName : NoticeController.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description : NoitceContoller
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/notice", produces = MediaTypes.HAL_JSON_VALUE)
public class NoticeController {

	private final NoticeRepository noticeRepository;
	private final NoticeValidator noticeValidator;
	private final NoticeService noticeService;
	
	public ResponseEntity createNotice(@RequestBody @Validated NoticeDto noticeDto,
									   Errors errors
	) 
	{
		if(errors.hasErrors()) return badRequest(errors);
		
		noticeValidator.noticeValidate(noticeDto, errors);
		if(errors.hasErrors()) return badRequest(errors);
		
		log.debug("GET /account/new HTTP/1.1");
		ResponseEntity result = noticeService.createNotice(noticeDto);
		
		return result;
	}
	
}

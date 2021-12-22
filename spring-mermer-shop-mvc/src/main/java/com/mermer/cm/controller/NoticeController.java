
package com.mermer.cm.controller;

import static com.mermer.cm.exception.ErrorsResource.badRequest;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.Notice;
import com.mermer.cm.entity.dto.NoticeDto;
import com.mermer.cm.service.NoticeService;
import com.mermer.cm.util.CurrentUser;
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
@RequestMapping(value = "/api/notice", produces = MediaTypes.HAL_JSON_VALUE)
@SuppressWarnings("rawtypes")
public class NoticeController {

	private final NoticeValidator noticeValidator;
	private final NoticeService noticeService;
	private final ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity createNotice(HttpServletRequest req,
									   @RequestBody @Validated NoticeDto noticeDto,
									   Errors errors,
									   @CurrentUser Account account
	) 
	{
		if(errors.hasErrors()) return badRequest(errors);
		
		noticeValidator.noticeValidate(noticeDto, errors);
		if(errors.hasErrors()) return badRequest(errors);
		

		Notice notice = modelMapper.map(noticeDto, Notice.class);
		log.debug("POST /notice/new HTTP/1.1");
		//현재 사용자 정보 세팅 - AccountAdapter 사용
		notice.setInster(account);
		notice.setMdfer(account);
		//현재 작성하는 ip주소 세팅
		notice.setWriterIp(req.getLocalAddr());
		ResponseEntity result = noticeService.createNotice(notice);
		
		return result;
	}
	
	@GetMapping
	public ResponseEntity queryNotice(Pageable pageable, 
									  PagedResourcesAssembler assembler
									 ) 
	{
		log.debug("GET /notice/new HTTP/1.1");
		
		return noticeService.queryNotice(pageable, assembler);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity getNoticeDetail(@PathVariable Long id) 
	{
		log.debug("GET /notice/1 HTTP/1.1");
		
		return noticeService.getNoticeDetail(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity updateNotice(@PathVariable Long id,
									   @RequestBody @Validated NoticeDto noticeDto,
									   Errors errors,
									   @CurrentUser Account account
									  ) 
	{
		log.debug("GET /notice/1 HTTP/1.1");
		if(errors.hasErrors())return badRequest(errors);
		
		noticeValidator.noticeValidate(noticeDto, errors);
		if(errors.hasErrors())return badRequest(errors);
		
		return noticeService.updateNotice(noticeDto, account, id);
	}
	
}

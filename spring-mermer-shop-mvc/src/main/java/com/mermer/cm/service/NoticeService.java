
package com.mermer.cm.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mermer.cm.controller.AccountController;
import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.Notice;
import com.mermer.cm.entity.dto.NoticeDto;
import com.mermer.cm.repository.AccountRepository;
import com.mermer.cm.repository.NoticeRepository;
import com.mermer.cm.resource.AccountResource;
import com.mermer.cm.resource.NoticeResource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @packageName : com.mermer.cm.service
 * @fileName : NoticeService.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description : Notice Service
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성
 */
@Service
@RequiredArgsConstructor //=> 자동으로 AccountRepository를 injection 처리
@Slf4j
public class NoticeService {

	private final AccountRepository accountRepository;
	private final NoticeRepository noticeRepository;
	private final ModelMapper modelMapper;
	/**
	 * @methond createNotice
	 * @param notice
	 * @return
	 * ResponseEntity
	 * @description 
	 */
	@Transactional
	public ResponseEntity createNotice(Notice notice) {	
		
		Notice result = noticeRepository.save(notice);
		
		//TODO 게시글의 댓글 정보 link return  
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(result.getId());
		URI createdUri = selfLinkBuilder.toUri();
	
		EntityModel<Optional> noticeResource = NoticeResource.of(Optional.of(result));//생성자 대신 static of 사용
		noticeResource.add((selfLinkBuilder).withSelfRel())
		.add(linkTo(AccountController.class).withRel("query-notice"))
		.add(selfLinkBuilder.withRel("update-notice"))
		.add(selfLinkBuilder.slash("reply").withRel("notice-reply-create"))
		.add(Link.of("/docs/index.html#resources-notice-create").withRel("profile"));
		
		return ResponseEntity.created(createdUri).body(noticeResource);
	}
	/**
	 * @method getClassLink
	 * @param accountId
	 * @return
	 * WebMvcLinkBuilder
	 * @description 
	 */
	private WebMvcLinkBuilder getClassLink(Long noticeId) {

		return linkTo(AccountController.class).slash(noticeId);
	}


}

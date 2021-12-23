
package com.mermer.cm.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mermer.cm.controller.AccountController;
import com.mermer.cm.controller.NoticeController;
import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.Notice;
import com.mermer.cm.entity.NoticeList;
import com.mermer.cm.entity.Reply;
import com.mermer.cm.entity.dto.NoticeDto;
import com.mermer.cm.repository.AccountRepository;
import com.mermer.cm.repository.NoticeReplyRepository;
import com.mermer.cm.repository.NoticeRepository;
import com.mermer.cm.resource.AccountResource;
import com.mermer.cm.resource.NoticeReplyResource;
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
	private final NoticeReplyRepository noticeReplyRepository;
	
	private final ModelMapper modelMapper;
	
	
	
	/**
	 * @method getClassLink
	 * @param accountId
	 * @return
	 * WebMvcLinkBuilder
	 * @description 
	 */
	private WebMvcLinkBuilder getClassLink(Long noticeId) {

		return linkTo(NoticeController.class).slash(noticeId);
	}
	
	
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
		.add(linkTo(NoticeController.class).withRel("query-notice"))
		.add(selfLinkBuilder.withRel("update-notice"))
		.add(selfLinkBuilder.slash("reply").withRel("notice-reply-create"))
		.add(Link.of("/docs/index.html#resources-notice-create").withRel("profile"));
		
		return ResponseEntity.created(createdUri).body(noticeResource);
	}
	
	/**
	 * @param assembler 
	 * @param pageable 
	 * @method queryNotice
	 * @return
	 * ResponseEntity
	 * @description 
	 */
	public ResponseEntity queryNotice(Pageable pageable, 
									  PagedResourcesAssembler assembler) 
	{

		//Page<Notice> page = noticeRepository.findAll(pageable);
		
		//전체 조회시 content 는 나오지 않도록 수정 
		Page<NoticeList> page = noticeRepository.findAllNoContent(pageable);
		
		var pagedResource = assembler.toModel(page, e -> NoticeResource.of(e).add(Link.of("/docs/index.html#resources-get-notice").withRel("profile")));
		pagedResource.add(Link.of("/docs/index.html#resources-notice-list").withRel("profile"));
		
		return ResponseEntity.ok(pagedResource);
	}


	/**
	 * @method getNoticeDetail
	 * @param noticeId
	 * @return
	 * ResponseEntity
	 * @description 
	 */
	public ResponseEntity getNoticeDetail(Long noticeId) {
		Optional<Notice> optionalNotice = noticeRepository.findById(noticeId);
		if(optionalNotice.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(noticeId);
		Notice notice = optionalNotice.get();
		
		//한번 읽을 때마다 조회수 1증가
		notice.updateReadCnt();
		notice = noticeRepository.save(notice);
		
		EntityModel<Notice> noticeResource = NoticeResource.of(notice)
											.add((selfLinkBuilder).withSelfRel())
											.add(Link.of("/docs/index.html#resources-notice-get").withRel("profile"))
											.add(selfLinkBuilder.withRel("update-notice"));
		
		return ResponseEntity.ok(noticeResource);
	}


	/**
	 * @method updateNotice
	 * @param noticeDto
	 * @param account 
	 * @param id
	 * @return
	 * ResponseEntity
	 * @description notice update - 작성자 본인만 수정할 수 있음
	 */
	public ResponseEntity updateNotice(Notice notice, Account account, Long noticeId) {

		Optional<Notice> optionalNotice = noticeRepository.findById(noticeId);
		
		if(optionalNotice.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Notice asNotice = optionalNotice.get();
		//본인 작성한 글이 아닌경우
		if(notice.getInster().getId() != account.getId()) {
			//권한 없음 리턴
			return ResponseEntity.status(401).build();
		}
		
		modelMapper.map(notice, asNotice);
		noticeRepository.save(asNotice);
		
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(noticeId);
		EntityModel<Notice> noticeResource = NoticeResource.of(notice)
				.add((selfLinkBuilder).withSelfRel())
				.add(Link.of("/docs/index.html#resources-notice-update").withRel("profile"));

		return ResponseEntity.ok(noticeResource);
	}


	/**
	 * @method createNoticeReply
	 * @param id
	 * @param reply
	 * @return notice-id 에 대한 reply 작성
	 * ResponseEntity
	 * @description 
	 */
	public ResponseEntity createNoticeReply(Long noticeId, Reply reply) {
		Optional<Notice> optionalNotice = noticeRepository.findById(noticeId);
		
		if(optionalNotice.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		reply.setNotice(optionalNotice.get());
		
		Reply result = noticeReplyRepository.save(reply);
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(noticeId).slash("reply");
		URI createdUri = selfLinkBuilder.toUri();
	
		EntityModel<Optional> noticeReplyResource = NoticeReplyResource.of(Optional.of(result));//생성자 대신 static of 사용
		noticeReplyResource.add((selfLinkBuilder).withSelfRel())
		.add(selfLinkBuilder.withRel("query-notice-reply"))
		.add(selfLinkBuilder.slash(result.getId()).withRel("update-notice-reply"))
		.add(Link.of("/docs/index.html#resources-notice-reply-create").withRel("profile"));
		
		return ResponseEntity.created(createdUri).body(noticeReplyResource);
	}


}

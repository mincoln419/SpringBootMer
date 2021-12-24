
package com.mermer.cm.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.List;
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
import com.mermer.cm.entity.dto.ReplyDto;
import com.mermer.cm.entity.type.UseYn;
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
@SuppressWarnings("rawtypes")
public class NoticeService {

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
	@SuppressWarnings("unchecked")
	public ResponseEntity queryNotice(Pageable pageable, 
									  PagedResourcesAssembler assembler) 
	{

		//Page<Notice> page = noticeRepository.findAll(pageable);
		
		//전체 조회시 content 는 나오지 않도록 수정 
		Page<NoticeList> page = noticeRepository.findAllNoContent(pageable);
		
		var pagedResource = assembler.toModel(page, e -> NoticeResource.of(e).add(Link.of("/docs/index.html#resources-get-notice").withRel("profile")));
		pagedResource.add(Link.of("/docs/index.html#resources-notice-list").withRel("profile"));
		
		//페이지가 안넘어갈 경우 first, next, last 링크 별도로 세팅
		if(page.getTotalElements() < page.getSize()) {
			Link sefLink = (Link)pagedResource.getLink("self").get();
			pagedResource.add(sefLink.withRel("first"));
			pagedResource.add(sefLink.withRel("next"));
			pagedResource.add(sefLink.withRel("last"));
		}
		
		return ResponseEntity.ok(pagedResource);
	}


	/**
	 * @method getNoticeDetail
	 * @param noticeId
	 * @return
	 * ResponseEntity
	 * @description 
	 */
	@Transactional
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
	@Transactional
	public ResponseEntity updateNotice(NoticeDto noticeDto, Account account, Long noticeId) {

		Optional<Notice> optionalNotice = noticeRepository.findById(noticeId);
		
		if(optionalNotice.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Notice notice = optionalNotice.get();
		
		//본인 작성한 글이 아닌경우
		if(notice.getInster().getId() != account.getId()) {
			//권한 없음 리턴
			return ResponseEntity.status(401).build();
		}
		
		modelMapper.map(noticeDto, notice);
	
		noticeRepository.save(notice);
		
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(noticeId);
		EntityModel<Notice> noticeResource = NoticeResource.of(notice)
				.add((selfLinkBuilder).withSelfRel())
				.add(Link.of("/docs/index.html#resources-notice-update").withRel("profile"));

		return ResponseEntity.ok(noticeResource);
	}

	
	/**
	 * @method deleteNotice
	 * @param account
	 * @param id
	 * @return
	 * ResponseEntity
	 * @description 공지사항 삭제 - 작성 본인만 삭제 가능 
	 */
	@Transactional
	public ResponseEntity deleteNotice(Account account, Long noticeId) {

		Optional<Notice> optionalNotice = noticeRepository.findById(noticeId);
		
		if(optionalNotice.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Notice notice = optionalNotice.get();
		
		//본인 작성한 글이 아닌경우
		if(notice.getInster().getId() != account.getId()) {
			//권한 없음 리턴
			return ResponseEntity.status(401).build();
		}
		
		notice.setUseYn(UseYn.N);
		noticeRepository.save(notice);
		
		//공지사항에 달린 댓글도 삭제
		List<Reply> children = noticeReplyRepository.findAllByNoticeId(noticeId);
		for(Reply reply : children) {
			reply.setUseYn(UseYn.N);
			noticeReplyRepository.save(reply);
		}
		
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(noticeId);
		EntityModel<Notice> noticeResource = NoticeResource.of(notice)
				.add((selfLinkBuilder).withSelfRel())
				.add(Link.of("/docs/index.html#resources-notice-delete").withRel("profile"));

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
	@Transactional
	public ResponseEntity createNoticeReply(Long noticeId, ReplyDto replyDto, Account account) {
		Optional<Notice> optionalNotice = noticeRepository.findById(noticeId);
		
		Reply reply = modelMapper.map(replyDto, Reply.class);
		
		if(optionalNotice.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		reply.setNotice(optionalNotice.get());
		Reply result = noticeReplyRepository.save(reply);
		
		
		/* 링크정보 hateous */
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(noticeId).slash("reply");
		URI createdUri = selfLinkBuilder.toUri();
	
		EntityModel<Optional> noticeReplyResource = NoticeReplyResource.of(Optional.of(result));//생성자 대신 static of 사용
		noticeReplyResource.add((selfLinkBuilder).withSelfRel())
		.add(selfLinkBuilder.withRel("query-notice-reply"))
		.add(selfLinkBuilder.slash(result.getId()).withRel("update-notice-reply"))
		.add(selfLinkBuilder.slash(result.getId()).withRel("create-notice-reply-re")) //대댓글 링크 추가
		.add(Link.of("/docs/index.html#resources-notice-reply-create").withRel("profile"));
		
		return ResponseEntity.created(createdUri).body(noticeReplyResource);
	}


	/**
	 * @method createNoticeReplyRe
	 * @param id
	 * @param replyId
	 * @param reply
	 * @return
	 * ResponseEntity
	 * @description 대댓글 작성 - 대댓글 조회, 수정, 삭제의 경우는 일반 댓글 수정과 동일한 로직으로 처리
	 */
	@Transactional
	public ResponseEntity createNoticeReplyRe(Long noticeId, Long replyId, Reply reply) {

		Optional<Notice> optionalNotice = noticeRepository.findById(noticeId);
		
		if(optionalNotice.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		reply.setNotice(optionalNotice.get());
		
		//대댓글 작성을 위한 세팅
		Optional<Reply> optionalNoticeReply = noticeReplyRepository.findById(replyId);
		reply.setParent(optionalNoticeReply.get());
		
		Reply result = noticeReplyRepository.save(reply);
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(noticeId).slash("reply").slash(result.getId());
		URI createdUri = selfLinkBuilder.toUri();
	
		EntityModel<Optional> noticeReplyResource = NoticeReplyResource.of(Optional.of(result));//생성자 대신 static of 사용
		noticeReplyResource.add((selfLinkBuilder).withSelfRel())
		.add(selfLinkBuilder.withRel("query-notice-reply"))
		.add(selfLinkBuilder.withRel("update-notice-reply"))
		.add(Link.of("/docs/index.html#resources-notice-reply-create-re").withRel("profile"));
		
		return ResponseEntity.created(createdUri).body(noticeReplyResource);
	}

	/**
	 * @method queryNoticeReply
	 * @param id
	 * @param pageable
	 * @param assembler
	 * @return
	 * ResponseEntity
	 * @description 댓글 전체 조회
	 */
	@SuppressWarnings("unchecked")
	public ResponseEntity queryNoticeReply(Long noticeId, Pageable pageable, PagedResourcesAssembler assembler) {
		//notice 검색
		Optional<Notice> optionalNotice = noticeRepository.findById(noticeId);
		if(optionalNotice.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		//전체 조회시 content 는 나오지 않도록 수정 
		Page<Reply> page = noticeReplyRepository.findAllReplyByNoticeId(noticeId, pageable);
		
		var pagedResource = assembler.toModel(page, e -> NoticeResource.of(e).add(Link.of("/docs/index.html#resources-get-notice-reply").withRel("profile")));
		pagedResource.add(Link.of("/docs/index.html#resources-query-notice-reply").withRel("profile"));
		
		//페이지가 안넘어갈 경우 first, next, last 링크 별도로 세팅
		if(page.getTotalElements() < page.getSize()) {
			Link sefLink = (Link)pagedResource.getLink("self").get();
			pagedResource.add(sefLink.withRel("first"));
			pagedResource.add(sefLink.withRel("next"));
			pagedResource.add(sefLink.withRel("last"));
		}
		return ResponseEntity.ok(pagedResource);
	}


	/**
	 * @method getNoticeReply
	 * @param id
	 * @param replyId
	 * @return
	 * ResponseEntity
	 * @description 댓글 한건 조회
	 */
	public ResponseEntity getNoticeReply(Long noticeId, Long replyId) {
		Optional<Notice> optionalNotice = noticeRepository.findById(noticeId);
		if(optionalNotice.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Reply result = noticeReplyRepository.getByIdAndNotice(noticeId, replyId).get();
		
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(noticeId).slash("reply").slash(replyId);
				
		EntityModel<Reply> replyResource = NoticeResource.of(result)
											.add((selfLinkBuilder).withSelfRel())
											.add(Link.of("/docs/index.html#resources-get-notice-reply").withRel("profile"))
											.add(selfLinkBuilder.withRel("update-notice-reply"));
		
		return ResponseEntity.ok(replyResource);
	}


	/**
	 * @method updateNoticeReply
	 * @param id
	 * @param replyId
	 * @param replyDto
	 * @return ResponseEntity  
	 * 
	 * @description 댓글 단건 수정 
	 */
	public ResponseEntity updateNoticeReply(Long noticeId, Long replyId, ReplyDto replyDto) {

		Optional<Notice> optionalNotice = noticeRepository.findById(noticeId);
		if(optionalNotice.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Reply reply = noticeReplyRepository.getByIdAndNotice(noticeId, replyId).get();
		
		modelMapper.map(replyDto, reply);
		
		Reply result = noticeReplyRepository.save(reply);
		
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(noticeId).slash("reply").slash(replyId);
		
		EntityModel<Reply> replyResource = NoticeResource.of(result)
											.add((selfLinkBuilder).withSelfRel())
											.add(Link.of("/docs/index.html#resources-update-notice-reply").withRel("profile"))
											;
		return ResponseEntity.ok(replyResource);
	}


	/**
	 * @method deleteNoticeReply
	 * @param id
	 * @param replyId
	 * @param account
	 * @return ResponseEntity
	 * @description 댓글 삭제
	 */
	@Transactional
	public ResponseEntity deleteNoticeReply(Long noticeId, Long replyId, Account account) {

		Optional<Notice> optionalNotice = noticeRepository.findById(noticeId);
		if(optionalNotice.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Reply reply = noticeReplyRepository.getByIdAndNotice(noticeId, replyId).get();
		//본인 작성한 글이 아닌경우
		if(reply.getInster().getId() != account.getId()) {
			//권한 없음 리턴
			return ResponseEntity.status(401).build();
		}
		//사용여부 N으로 변경
		reply.setUseYn(UseYn.N);
		
		Reply result = noticeReplyRepository.save(reply);
		
		//대댓글 삭제 -> reply가 부모인 데이터가 있으면 자식 데이터의 경우 cascade 처리
		List<Reply> children = noticeReplyRepository.findAllReplyByNoticeId(noticeId, replyId);
		for(Reply child : children) {
			child.setUseYn(UseYn.N);
			noticeReplyRepository.save(child);
		}
		
		WebMvcLinkBuilder selfLinkBuilder = getClassLink(noticeId).slash("reply");
		
		EntityModel<Reply> replyResource = NoticeResource.of(result)
											.add(selfLinkBuilder.slash(replyId).withSelfRel())
											.add(Link.of("/docs/index.html#resources-delete-notice-reply").withRel("profile"))
											;
		return ResponseEntity.ok(replyResource);
	}





}

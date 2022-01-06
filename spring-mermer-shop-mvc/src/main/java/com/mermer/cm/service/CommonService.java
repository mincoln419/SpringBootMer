package com.mermer.cm.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mermer.cm.controller.CommonController;
import com.mermer.cm.entity.UpLoadFile;
import com.mermer.cm.repository.UpLoadFileRepository;
import com.mermer.cm.resource.NoticeResource;
import com.mermer.cm.util.DevUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * @packageName : com.mermer.cm.service
 * @fileName : CommonService.java
 * @author : Mermer
 * @date : 2022.01.06
 * @description : ===========================================================
 *              DATE AUTHOR NOTE
 *              -----------------------------------------------------------
 *              2022.01.06 Mermer 최초 생성
 */
@Service
@RequiredArgsConstructor // => 자동으로 필드 injection 처리
@Slf4j
@SuppressWarnings("rawtypes")
public class CommonService {

	final UpLoadFileRepository upLoadFileRepository;

	/**
	 * @method createFile
	 * @param upLoadFile
	 * @return ResponseEntity
	 * @description
	 */
	@Transactional
	public ResponseEntity createFile(UpLoadFile upLoadFile) {

		UpLoadFile result = upLoadFileRepository.save(upLoadFile);

		// TODO 게시글의 댓글 정보 link return
		WebMvcLinkBuilder selfLinkBuilder = linkTo(CommonController.class).slash("img").slash(result.getFileId());
		URI createdUri = selfLinkBuilder.toUri();

		EntityModel<Optional> imageResource = NoticeResource.of(Optional.of(result));// 생성자 대신 static of 사용
		imageResource.add((selfLinkBuilder).withSelfRel())
				.add(linkTo(CommonController.class).withRel("image-select"))
				.add(Link.of("/docs/index.html#resources-image-create").withRel("profile"));

		return ResponseEntity.created(createdUri).body(imageResource);
	}

}

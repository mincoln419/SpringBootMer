package com.mermer.cm.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mermer.cm.controller.CommonController;
import com.mermer.cm.entity.UpLoadFile;
import com.mermer.cm.repository.UpLoadFileRepository;
import com.mermer.cm.resource.UpLoadFileListResource;
import com.mermer.cm.resource.UpLoadFileResource;
import com.mermer.view.controller.BaseViewController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	 * @param 
	 * @return ResponseEntity
	 * @description
	 */
	@Transactional
	public ResponseEntity createFile(List<UpLoadFile> list) {

		List<UpLoadFile> resultList = new ArrayList<>();
		for(UpLoadFile upLoadFile : list)resultList.add(upLoadFileRepository.save(upLoadFile));
		
		WebMvcLinkBuilder selfLinkBuilder = linkTo(CommonController.class).slash("img");
		URI createdUri = selfLinkBuilder.toUri();

		List<EntityModel<UpLoadFile>> detail= resultList.stream().map(result -> {
			return UpLoadFileResource.of(result, selfLinkBuilder.withSelfRel())
					.add((linkTo(BaseViewController.class)).slash(result.getFileName()).withRel("select-image"));
		}).collect(Collectors.toList());
		
		CollectionModel<EntityModel<UpLoadFile>> imageResource = UpLoadFileListResource.of(detail)
										.add((selfLinkBuilder).withSelfRel())
										.add(Link.of("/docs/index.html#resources-image-create").withRel("profile"));

		return ResponseEntity.created(createdUri).body(imageResource);
	}

}

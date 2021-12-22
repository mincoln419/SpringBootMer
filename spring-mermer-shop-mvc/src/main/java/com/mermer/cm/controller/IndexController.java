/**
 * @packageName : com.mermer.cm.controller
 * @fileName : IndexController.java 
 * @author : Mermer 
 * @date : 2021.12.17 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.17 Mermer 최초 생성
 */
package com.mermer.cm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/* 
 * @description: 
 */
@RestController
public class IndexController {
	
	@GetMapping("/api")
	public RepresentationModel index() {
		var index = new RepresentationModel();
		// api 접근시 잘못된 주소인 경우는 공지사항 조회 link return
		index.add(linkTo(NoticeController.class).withRel("index"));
		return index;
	}
}

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

import java.util.Optional;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/* 
 * @description: 
 */
@RestController
public class IndexController {
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/api")
	public RepresentationModel apiIndex() {
		var index = new RepresentationModel();
		// api 접근시 잘못된 주소인 경우는 공지사항 조회 link return
		index.add(Link.of("/docs/index.html").withRel("docs"));
		return index;
	}
	
	@GetMapping("/")
	public RedirectView index() {
		var index = new RedirectView ("/docs/index.html");
		return index;
	}
}

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
		index.add(linkTo(AccountController.class).withRel("index"));
		return index;
	}
}

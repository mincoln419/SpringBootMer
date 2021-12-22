
package com.mermer.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @packageName : com.mermer.view.controller
 * @fileName : BulletinController.java 
 * @author : Mermer 
 * @date : 2021.12.22 
 * @description : 자유게시판 view controller
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.22 Mermer 최초 생성
 */
@Controller("/bulletin")
public class BulletinController {
	
	@GetMapping
	public String bulletin() {
	
		return "bulletin/list";
	}
	
	@GetMapping("/{id}")
	public String bulletinDetail(@PathVariable Long id) {
		
		return "/bulletin/detail";
	}
	
	@PostMapping
	public String bulletinCreate() {
		
		return "/bulletin/edit";
	}
	
	@PutMapping("/{id}")
	public String bulletinDetailEdit(@PathVariable Long id) {
		
		return "/bulletin/edit";
	}

}


package com.mermer.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @packageName : com.mermer.view.controller
 * @fileName : NoticeController.java 
 * @author : Mermer 
 * @date : 2021.12.22 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.22 Mermer 최초 생성
 */
@RequestMapping("/notice")
@Controller
public class NoticeViewController {
	
	@GetMapping
	public String noticeList() {
	
		return "/notice/list";
	}
	
	@GetMapping("/{id}")
	public String noticeDetail(@PathVariable Long id) {
		
		return "/notice/detail";
	}
	
	@PostMapping
	public String noticeCreate() {
		
		return "/notice/edit";
	}
	
	@PutMapping("/{id}")
	public String noticeDetailEdit(@PathVariable Long id) {
		
		return "/notice/edit";
	}

}

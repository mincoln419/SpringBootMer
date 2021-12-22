
package com.mermer.view.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @packageName : com.mermer.view.controller
 * @fileName : BaseController.java 
 * @author : Mermer 
 * @date : 2021.12.22 
 * @description : view base Controller
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.22 Mermer 최초 생성
 */
@Controller(value = "/")
public class BaseViewController implements ErrorController{// implements 해야 error 페이지에 대한 주도권을 가져온다

	@GetMapping
	public String index() {
		return "index";
	}

	@RequestMapping("/error") //모든 리퀘스트에 대응
	public String error() {
		return "error";
	}
	
}

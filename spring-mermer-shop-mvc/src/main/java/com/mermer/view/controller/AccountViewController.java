
package com.mermer.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @packageName : com.mermer.view.controller
 * @fileName : AuthController.java 
 * @author : Mermer 
 * @date : 2021.12.22 
 * @description : 회원가입/로그인 핸들러 매소드 매핑
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.22 Mermer 최초 생성
 */
@Controller
public class AccountViewController {

	@GetMapping("/login")
	public String login() {
		return "auth/login"; // 설정된 경로의 login.jsp 또는 login.html 화면으로 리턴 
	}
	
	@GetMapping("/sign-up")
	public String signUp() {
		return "auth/signUp"; // 설정된 경로의 login.jsp 또는 login.html 화면으로 리턴 
	}
}

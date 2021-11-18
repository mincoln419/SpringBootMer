package com.mermer.user;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/*
 * restcontroller anot. 있으면 responsebody.. anot가 필요없음 - 그냥 컨트롤러를 사용하는 경우에는 자동 컨버팅되지 않음.
 * 
 * */
@RestController
public class UserContorller {

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@DeleteMapping("/hello")
	public String hello_delete() {
		return "hello";
	}
	
	@PostMapping("/user/create")
	public User create(@RequestBody User user) {
		
		//composition 타입 리턴인 경우에는 json으로 컨버젼
		
		return user;
	}
}

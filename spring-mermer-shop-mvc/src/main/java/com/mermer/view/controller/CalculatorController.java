/**
 * @packageName : com.mermer.view.controller
 * @fileName : CalculatorController.java 
 * @author : Mermer 
 * @date : 2021.12.22 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.22 Mermer 최초 생성
 */
package com.mermer.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/calculator")
@Controller
public class CalculatorController {

	@GetMapping
	public String lawCal() {
		return "/calculator/main";
	}
	
}

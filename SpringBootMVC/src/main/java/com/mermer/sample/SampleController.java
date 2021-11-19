package com.mermer.sample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {

	@GetMapping("helloMer")
	public String hello(Model model) {
		model.addAttribute("username", "mermer");
		return "hello";
	}
	
}

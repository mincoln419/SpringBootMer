package com.mermer.sample;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

	@GetMapping("/helloMer")
	public String hello(Model model) {
		model.addAttribute("username", "mermer");
		return "hello";
	}
	
	@GetMapping("/errors")
	public void error() {
		throw new SampleExcpetion();
	}
	
	@ExceptionHandler(SampleExcpetion.class)
	public @ResponseBody AppError sampleError(SampleExcpetion e){
		AppError appError = new AppError();
		appError.setMessage("error.app.key");
		appError.setCode("-12231");
		return appError;
	}
	
}


package com.mermer.view.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mermer.cm.entity.Account;
import com.mermer.cm.util.AppProperties;
import com.mermer.cm.util.RestTemplateRequest;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class AccountViewController {

	@Autowired
	private RestTemplateRequest restTemplateRequest;
	
	@Autowired
	private AppProperties appProperties;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@GetMapping("/login")
	public String loginView(Model model) throws JsonProcessingException {
		
		return "auth/login"; // 설정된 경로의 login.jsp 또는 login.html 화면으로 리턴 
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(Model model, HttpServletRequest req) throws JsonProcessingException {
		
		MultiValueMap<String, String> parseMap = new LinkedMultiValueMap<>();
		
		String clientId = appProperties.getClientId();
		String clientSecret = appProperties.getClientSecret();
		
		String username = appProperties.getAdminName();
		String password = appProperties.getAdminPass();

		String uri = req.getRequestURL().toString().replace(req.getPathInfo(), "/oauth/token");

		parseMap.add("username", username);
		parseMap.add("password", password);
		parseMap.add("grant_type", "password");
		
		
		RestTemplate restTemplate= new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(clientId, clientSecret, Charset.forName("UTF-8"));
		
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class, parseMap, MultiValueMap.class);
			 log.debug("header", response.getHeaders());
			 log.debug("body", response.getBody());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return response; // 설정된 경로의 login.jsp 또는 login.html 화면으로 리턴 
	}
	
	@GetMapping("/sign-up")
	public String signUp() {
		return "auth/signUp"; // 설정된 경로의 login.jsp 또는 login.html 화면으로 리턴 
	}
	
}

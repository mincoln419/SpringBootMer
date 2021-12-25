
package com.mermer.cm.util;


import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mermer.cm.entity.Account;

import lombok.Getter;

/**
 * @packageName : com.mermer.cm.util
 * @fileName : RestTemplateRequest.java 
 * @author : Mermer 
 * @param <T>
 * @date : 2021.12.25 
 * @description : restTemplate으로 api 호출 공통부
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.25 Mermer 최초 생성
 */
@Getter
@Component
public class RestTemplateRequest<T> extends RestTemplate{
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ModelMapper modelMapper; 
	
	private HttpHeaders headers;
	private HttpEntity<T> httpEntity;
	private String param;
	private String body;
	
	
	
	/* 일반 - json parse 
	 * 공통으로 사용하기 위해 map을 사용 (view controller에서 modelMapper 로 변경처리)
	 * */
	public RestTemplateRequest(){
		super();
	}



	/**
	 * @method of
	 * @param parseMap
	 * @return
	 * RestTemplateRequest<Account>
	 * @throws JsonProcessingException 
	 * @description 
	 */
	public RestTemplateRequest<T> of(Map<String, Object> bodyMap, String token) throws JsonProcessingException {
		RestTemplateRequest<T> request= new RestTemplateRequest<>();
		request.headers = new HttpHeaders();
		//request.headers.setContentType(MediaType.APPLICATION_JSON);
		request.body = objectMapper.writeValueAsString(bodyMap);

		return request;
	}
	
	
	/* */
	
}


package com.mermer.cm.util;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @packageName : com.mermer.cm.util
 * @fileName : AppProperties.java 
 * @author : Mermer 
 * @date : 2021.12.19 
 * @description : properties파일의 정적 정보를 사용하기 위한 class
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.19 Mermer 최초 생성
 */
@Component
@ConfigurationProperties(prefix = "mer-app")
@Getter @Setter
public class AppProperties {
	
	//최초 생성되는 관리자 계정정보
	@NotBlank
	private String adminName;
	@NotBlank
	private String adminPass;
	
	@NotBlank
	private String userName;
	@NotBlank
	private String userPass;
	
	@NotBlank
	private String guestName;
	@NotBlank
	private String guestPass;
	
	//토큰정보
	@NotBlank
	private String clientId;
	@NotBlank
	private String clientSecret;
	
	
}

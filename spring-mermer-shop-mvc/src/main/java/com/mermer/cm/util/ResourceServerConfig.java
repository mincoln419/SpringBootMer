
package com.mermer.cm.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * @packageName : com.mermer.cm.util
 * @fileName : ResourceServerConfig.java 
 * @author : Mermer 
 * @date : 2021.12.19 
 * @description : 리소스 관리 서버 config
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.19 Mermer 최초 생성
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("account");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.anonymous()//anonymous만 사용가능
		.and()
		.authorizeRequests()
			.mvcMatchers(HttpMethod.POST, "/account")
				.permitAll()//모두 사용 가능
			.mvcMatchers(HttpMethod.GET, "/notice/**")
				.permitAll()//모두 사용 가능
			.anyRequest()
				.authenticated()
			.and()
		.exceptionHandling()
			.accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
	
	
	
	
	
}

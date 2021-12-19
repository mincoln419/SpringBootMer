
package com.mermer.cm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import com.mermer.cm.service.AccountService;

/**
 * @packageName : com.mermer.cm.util
 * @fileName : SecurityConfig.java 
 * @author : Mermer 
 * @date : 2021.12.19 
 * @description : 사용자 인증 관련 config
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.19 Mermer 최초 생성
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(accountService)
		.passwordEncoder(encoder);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers("/docs/index.html");
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	
	}
	
}

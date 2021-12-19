
package com.mermer.cm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.mermer.cm.service.AccountService;

/**
 * @packageName : com.mermer.cm.util
 * @fileName : AuthServerConfig.java 
 * @author : Mermer 
 * @date : 2021.12.19 
 * @description : 사용자 인증서버 configuration
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.19 Mermer 최초 생성
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	TokenStore tokenStore;
	
	@Autowired
	AppProperties appProperties;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	
		security.passwordEncoder(encoder);
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory()
			.withClient(appProperties.getClientId())
			.authorizedGrantTypes("password", "refresh_token")
			.scopes("read", "write")
			.secret(this.encoder.encode(appProperties.getClientSecret()))
			.accessTokenValiditySeconds(10 * 60)
			.refreshTokenValiditySeconds(6 * 10 * 60);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
			.userDetailsService(accountService)
			.tokenStore(tokenStore);
	}
}

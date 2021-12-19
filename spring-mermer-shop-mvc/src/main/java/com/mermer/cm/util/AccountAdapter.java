package com.mermer.cm.util;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.mermer.cm.entity.Account;
import com.mermer.cm.entity.type.AccountRole;

/**
 * @packageName : com.mermer.cm.util
 * @fileName : AccountAdapter.java 
 * @author : Mermer 
 * @date : 2021.12.19 
 * @description :인증받은 사용자(User)가 Account에 속하는지 체크하기 위한 adpater
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2021.12.19 Mermer 최초 생성
 */
public class AccountAdapter extends User {

	private static final long serialVersionUID = -2960914085354919176L;
	private Account account;
		
	public AccountAdapter(Account account) {
		super(account.getLoginId(), account.getPass(), authorities(account.getAccountRole()));
		this.account = account;
	}

	private static Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
		return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
				.collect(Collectors.toSet());
	}
	
	public Account getAccount() {
		return account;
	}

}

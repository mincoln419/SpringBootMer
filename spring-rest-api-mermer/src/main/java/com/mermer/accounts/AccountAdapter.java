package com.mermer.accounts;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/*
 * 인증받은 사용자(User)가 Account에 속해 있어야 하므로 이를 체크하기 위한 adpater class
 * 
 */
public class AccountAdapter extends User{
	
	private Account account;
	
	public AccountAdapter(Account account) {
		super(account.getEmail(), account.getPassword(), authorities(account.getRoles()));
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

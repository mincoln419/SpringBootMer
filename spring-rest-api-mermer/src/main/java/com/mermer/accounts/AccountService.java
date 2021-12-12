package com.mermer.accounts;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService{

	@Autowired
	AccountRepository accountRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findbyEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return new User(account.getEmail(), account.getPassword(), authorities(account.getRoles()));
	}

	private Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
		return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
				.collect(Collectors.toSet());
	}

}

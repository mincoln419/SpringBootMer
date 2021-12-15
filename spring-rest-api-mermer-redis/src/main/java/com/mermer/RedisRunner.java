package com.mermer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisRunner implements ApplicationRunner{

	@Autowired
	StringRedisTemplate redisTemplate;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		ValueOperations<String, String> values = redisTemplate.opsForValue();
		values.set("user", "meremer");
		values.set("age", "19");
		values.set("hello", "world");
		
		Account account = new Account();
		account.setEmail("mermer@naver.com");
		account.setUsername("mermer");
		
		accountRepository.save(account);
		
		Optional<Account> byId = accountRepository.findById(account.getId());
		System.out.println(byId.get().getUsername());
		System.out.println(byId.get().getEmail());
		
		
	}
	
	
}

package com.mermer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoRunner implements ApplicationRunner{

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		Account account = new Account();
		account.setEmail("mermer_mongoTemplte@naver.com");
		account.setName("mermer");
		
		mongoTemplate.insert(account);
		
		System.out.println("finished");
		
		
		account.setEmail("mermer_mongoReposit@naver.com");
		account.setName("mermer");
		accountRepository.save(account);
	}

	
}

package com.mermer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

@DataMongoTest
class SpringRestApiMermerMongoDbApplicationTests {

	@Autowired
	AccountRepository accountRepository;
	
	@Test
	public void findByEmail() {
		
		Account account = new Account();
		String name = "mermer11";
		account.setEmail("mermer@naver.com");
		account.setName(name);
		
		accountRepository.save(account);
		
		Optional<Account> byId = accountRepository.findByEmail(account.getEmail());
		assertThat(!byId.isEmpty());
		assertThat(byId.get().getName()).isEqualTo(name);
	} 
}

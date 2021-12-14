package com.mermer;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.mermer.account.Account;
import com.mermer.account.AccountRepository;


@RunWith(SpringRunner.class)
@DataJpaTest//Slicing 테스트
//@SpringBootTest // Integration 테스트
public class AccountRepositoryTest {
	
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Test
	public void di() {
		try(Connection connection = dataSource.getConnection()){
			DatabaseMetaData metaData = connection.getMetaData();
			System.out.println(metaData.getURL());
			System.out.println(metaData.getDriverName());
			System.out.println(metaData.getUserName());
		}catch(Exception e) {
			
		}
		
		Account account = Account.builder()
				.username("mermer")
				.password("pass")
				.build();
		
		Account newAccount = accountRepository.save(account);
		assertThat(newAccount).isNotNull();
		
		Optional<Account> exsitAccount = accountRepository.findByUsername(newAccount.getUsername());
		assertThat(exsitAccount.orElseGet(null).getPassword()).isEqualTo(account.getPassword());
		
	}
	
}

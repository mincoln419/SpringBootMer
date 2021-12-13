package com.mermer.accounts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer>{

	Optional<Account> findByEmail(String username); //카멜케이스 안지키면 ByEmail 로 query 만들어지지 않는다...

}

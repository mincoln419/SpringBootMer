package com.mermer.cm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mermer.cm.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	/**
	 * @methond findByAccountId
	 * @param id
	 * @return
	 * Optional<Account>
	 * @description 
	 */
	Optional<Account> findByAccountId(Long id);

}

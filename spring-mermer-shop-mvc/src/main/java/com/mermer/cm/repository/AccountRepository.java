package com.mermer.cm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mermer.cm.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	/**
	 * @method findByLoginId
	 * @param login
	 * @return
	 * Account
	 * @description 
	 */
	Optional<Account> findByLogin(String login);

	/**
	 * @method findByEmail
	 * @param login
	 * @return
	 * String
	 * @description 
	 */
	Optional<Account> findByEmail(String login);

	/**
	 * @method findByHpNum
	 * @param login
	 * @return
	 * String
	 * @description 
	 */
	Optional<Account> findByHpNum(String login);
	

}

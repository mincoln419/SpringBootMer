package com.mermer.cm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mermer.cm.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}

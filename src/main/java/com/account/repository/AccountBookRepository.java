package com.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.account.entity.AccountBook;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long>  {

	
}

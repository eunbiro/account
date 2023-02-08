package com.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.account.entity.AccountBook;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long>  {

	List<AccountBook> findByMemberId(Long MemberId);
}

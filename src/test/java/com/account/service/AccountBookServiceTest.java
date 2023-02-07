package com.account.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.account.entity.AccountBook;
import com.account.entity.SubCategory;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class AccountBookServiceTest {

	@Autowired
	AccountBookService accountBookService;
	
	@Autowired
	SubCategory subCategory;
	
	public AccountBook createAccountBook() {
		
		AccountBook accountBook = new AccountBook();
		
		accountBook.setAccStatus("0");
		accountBook.setMoney((long) 10000);
		accountBook.setOtherCtgName(null);
		accountBook.setAccTitle("점심");
		accountBook.setAccDtlMemo("오늘점심은어디로");
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime date = LocalDateTime.parse("20230207", formatter);
		accountBook.setAccDate(date);
		
		return accountBook;
	}
	
	@Test
	@DisplayName("기입 테스트")
	void saveAccounttest() {
		AccountBook accountBook = createAccountBook();
		AccountBook savedAccountBook = accountBookService.saveAccount(accountBook);
		
		assertEquals(accountBook.getAccDate(), savedAccountBook.getAccDate());
	}

}

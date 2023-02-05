package com.account.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.account.dto.MainCategoryDto;
import com.account.dto.SubCategoryDto;
import com.account.entity.AccountBook;
import com.account.entity.MainCategory;
import com.account.entity.SubCategory;
import com.account.repository.AccountBookRepository;
import com.account.repository.MainCategoryRepository;
import com.account.repository.SubCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountBookService  {
	
	private final SubCategoryRepository subCategoryRepository;
	
	private final MainCategoryRepository mainCategoryRepository;
	
	private final AccountBookRepository accountBookRepository;
	
	// 가계부 등록
	public AccountBook saveAccount(AccountBook accountBook) {
		
		return accountBookRepository.save(accountBook);
	}
	
	@Transactional(readOnly = true)
	public List<MainCategoryDto> getMainCtg() {
		
		List<MainCategory> mainCtgList = mainCategoryRepository.findAll();
		List<MainCategoryDto> mainCtgDtoList = new ArrayList<>();
		
		for (MainCategory MainCategory : mainCtgList) {

			
			MainCategoryDto mainCategoryDto = MainCategoryDto.of(MainCategory);
			mainCtgDtoList.add(mainCategoryDto);
		}
		
		return mainCtgDtoList;
	}

}

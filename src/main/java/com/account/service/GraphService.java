package com.account.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.account.dto.AccountBookDto;
import com.account.dto.AccountBookSearchDto;
import com.account.dto.MainCategoryDto;
import com.account.dto.SubCategoryDto;
import com.account.entity.AccountBook;
import com.account.repository.AccountBookRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GraphService {

	private final AccountBookService accountBookService;
	private final AccountBookRepository accountBookRepository;
	
	public List<AccountBookDto> getSearchAccBook(AccountBookSearchDto AccountBookSearchDto) {
		
		LocalDate accDate = accDateAfter(AccountBookSearchDto.getSearchDateType());
		List<AccountBook> accountBookList = accountBookRepository.findByResult(accDate, AccountBookSearchDto.getMainCtgId());
		List<AccountBookDto> accountBookDtoList = new ArrayList<>();
		
		for (AccountBook accountBook : accountBookList) {
			
			AccountBookDto accountBookDto = new AccountBookDto();
			accountBookDto.setAccDate(accountBookService.dateToString(accountBook.getAccDate()));
			accountBookDto.setAccTitle(accountBook.getAccTitle());
			accountBookDto.setMoney(accountBook.getMoney());
			
			MainCategoryDto mainCategoryDto = new MainCategoryDto();
			mainCategoryDto.setMainCtgName(accountBook.getSubCategory().getMainCategory().getMainCtgName());
			
			SubCategoryDto subCategoryDto = new SubCategoryDto();
			subCategoryDto.setMainCategoryDto(mainCategoryDto);
			
			accountBookDto.setSubCategoryDto(subCategoryDto);
			
			accountBookDtoList.add(accountBookDto);
		}
		
		return accountBookDtoList;
	}
	
	private LocalDate accDateAfter(String searchDateType) {
		
		LocalDate localDate = LocalDate.now();
		
		if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
			
			return null;
		} else if (StringUtils.equals("1d", searchDateType)) {

			localDate = localDate.minusDays(1);
		} else if (StringUtils.equals("1w", searchDateType)) {
			
			localDate = localDate.minusWeeks(1);
		} else if (StringUtils.equals("1m", searchDateType)) {
			
			localDate = localDate.minusMonths(1);
		} else if (StringUtils.equals("6m", searchDateType)) {
			
			localDate = localDate.minusMonths(6);
		}
		
		return localDate;
	}
}

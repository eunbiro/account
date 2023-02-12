package com.account.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.account.dto.AccountBookDto;
import com.account.dto.AccountBookSearchDto;
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
	
	public AccountBookDto getSearchAccBook(AccountBookSearchDto AccountBookSearchDto) {
		
		LocalDate accDate = accDateAfter(AccountBookSearchDto.getSearchDateType());
		List<SubCategoryDto> subCategoryDto = accountBookService.getSubCtg(AccountBookSearchDto.getMainCtgId());
		
		
		List<AccountBook> accountBookList = accountBookRepository.findByResult(accDate, AccountBookSearchDto.getMainCtgId());
		
		List<AccountBookSearchDto>
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

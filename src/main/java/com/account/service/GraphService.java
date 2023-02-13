package com.account.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.account.dto.AccountBookDto;
import com.account.dto.AccountBookSearchDto;
import com.account.dto.MainCategoryDto;
import com.account.dto.SubCategoryDto;
import com.account.entity.AccountBook;
import com.account.entity.MainCategory;
import com.account.entity.Member;
import com.account.repository.AccountBookRepository;
import com.account.repository.MainCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GraphService {

	private final AccountBookService accountBookService;
	private final AccountBookRepository accountBookRepository;
	private final MainCategoryRepository mainCategoryRepository;
	private final MemberService memberService;
	
	// 기간별 목록조회시 데이터 가져옴
	public List<AccountBookDto> getSearchAccBook(AccountBookSearchDto AccountBookSearchDto, String userId) {
		
		List<AccountBook> accountBookList;
		List<AccountBookDto> accountBookDtoList = new ArrayList<>();
		Member member = memberService.getMember(userId);
		Long memberId = member.getId();
		LocalDate accDate = accDateAfter(AccountBookSearchDto.getSearchDateType());
		
		if (accDate == null) {
			
			accountBookList = accountBookRepository.findByMemberId(memberId);
		} else {
			
			accountBookList = accountBookRepository.findByMemberIdAndAccDateAfter(memberId, accDate);
		}
		
		if (AccountBookSearchDto.getMainCtgId() == null) {
			
			for (AccountBook accountBook : accountBookList) {
				
				AccountBookDto accountBookDto = new AccountBookDto();
				accountBookDto.setAccDate(accountBookService.dateToString(accountBook.getAccDate()));
				accountBookDto.setAccTitle(accountBook.getAccTitle());
				accountBookDto.setMoney(accountBook.getMoney());
				
				accountBookDto.setSubCategoryDto(getSubCategoryDto(accountBook));
				
				accountBookDtoList.add(accountBookDto);
			}
		} else {
			
			for (AccountBook accountBook : accountBookList) {
				
				AccountBookDto accountBookDto = new AccountBookDto();
				
				if (accountBook.getSubCategory().getMainCategory().getId() == AccountBookSearchDto.getMainCtgId()) {
					
					accountBookDto.setAccDate(accountBookService.dateToString(accountBook.getAccDate()));
					accountBookDto.setAccTitle(accountBook.getAccTitle());
					accountBookDto.setMoney(accountBook.getMoney());
					
					accountBookDto.setSubCategoryDto(getSubCategoryDto(accountBook));
					
					accountBookDtoList.add(accountBookDto);
				}
			}
		}
		
		return accountBookDtoList;
	}
	
	// 기간별 그래프조회시 데이터 가져옴
	public List<AccountBookDto> getSearchGraph(AccountBookSearchDto AccountBookSearchDto, String userId) {
		
		List<AccountBook> accountBookList;
		List<AccountBookDto> accountBookDtoList = new ArrayList<>();
		Member member = memberService.getMember(userId);
		Long memberId = member.getId();
		LocalDate accDate = accDateAfter(AccountBookSearchDto.getSearchDateType());
		
		if (accDate == null) {
			
			accountBookList = accountBookRepository.findByMemberId(memberId);
		} else {
			
			accountBookList = accountBookRepository.findByMemberIdAndAccDateAfter(memberId, accDate);
		}
		
		for (AccountBook accountBook : accountBookList) {
			
			AccountBookDto accountBookDto = new AccountBookDto();
			accountBookDto.setAccDate(accountBookService.dateToString(accountBook.getAccDate()));
			accountBookDto.setAccTitle(accountBook.getAccTitle());
			accountBookDto.setMoney(accountBook.getMoney());
			
			accountBookDto.setSubCategoryDto(getSubCategoryDto(accountBook));
			
			accountBookDtoList.add(accountBookDto);
		}
		
		return accountBookDtoList;
	}
	
	// 카테고리 전체조회시 가져오는거
	public SubCategoryDto getSubCategoryDto(AccountBook accountBook) {
		
		MainCategoryDto mainCategoryDto = new MainCategoryDto();
		mainCategoryDto.setId(accountBook.getSubCategory().getMainCategory().getId());
		mainCategoryDto.setMainCtgName(accountBook.getSubCategory().getMainCategory().getMainCtgName());
		
		SubCategoryDto subCategoryDto = new SubCategoryDto();
		subCategoryDto.setId(accountBook.getSubCategory().getId());
		subCategoryDto.setSubCtgName(accountBook.getSubCategory().getSubCtgName());
		subCategoryDto.setMainCategoryDto(mainCategoryDto);
		
		return subCategoryDto;
	}
	
	// 카테고리 지정조회시 가져오는거
//	public MainCategoryDto getMainCategoryDto(AccountBookSearchDto AccountBookSearchDto) {
//		
//		MainCategoryDto mainCategoryDto = new MainCategoryDto();
//		MainCategory mainCategory = mainCategoryRepository.findById(AccountBookSearchDto.getMainCtgId())
//														  .orElseThrow(EntityNotFoundException::new);
//		
//		mainCategoryDto.setId(mainCategory.getId());
//		mainCategoryDto.setMainCtgName(mainCategory.getMainCtgName());
//		
//		return mainCategoryDto;
//	}
	
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

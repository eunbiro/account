package com.account.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.account.dto.AccountBookDto;
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
	
	// 메인 카테고리 가져옴
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
	
	// 섭카테고리 가져옴
	public List<SubCategoryDto> getSubCtg(String mainCtgId) {
		
		List<SubCategory> subCtgList = subCategoryRepository.findByMainCategory(mainCtgId);
		List<SubCategoryDto> subCtgDtoList = new ArrayList<>();
		
		for (SubCategory subCategory : subCtgList) {
			
			SubCategoryDto subCategoryDto = SubCategoryDto.of(subCategory);
			subCtgDtoList.add(subCategoryDto);
		}
		
		return subCtgDtoList;
	}
	
	// 가계부 가져옴
	public List<AccountBookDto> getAccBook(Long memberId) {
		
		List<AccountBookDto> accBookDtoList = new ArrayList<>();
		List<AccountBook> accBookList = accountBookRepository.findByMemberId(memberId);
		
		for (AccountBook accountBook : accBookList) {
			
			AccountBookDto accBookDto = new AccountBookDto();
			String accDate = accountBook.getAccDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			accBookDto.setAccDate(accDate);
			accBookDto.setAccStatus(accountBook.getAccStatus());
			accBookDto.setMoney(accountBook.getMoney());
			accBookDto.setAccTitle(accountBook.getAccTitle());
			
			SubCategoryDto subCategoryDto = new SubCategoryDto();
			subCategoryDto.setId(accountBook.getSubCategory().getId());
			accBookDto.setSubCategoryDto(subCategoryDto);
			
			if (accountBook.getOtherCtgName() != null) {
				
				accBookDto.setOtherCtgName(accountBook.getOtherCtgName());
			}
			
			if (accountBook.getAccDtlMemo() != null) {
				
				accBookDto.setAccDtlMemo(accountBook.getAccDtlMemo());
			}
			
			accBookDtoList.add(accBookDto);
		}
		
		return accBookDtoList;
	}
	
	// TODO : main - 날짜 그룹화해서 가져오는 repository
	public List<AccountBookDto> getAccDate(String MemberId) {
		
		List<AccountBook> accDateList = accountBookRepository.findAccDateGroupAccDateByMemberId(MemberId);
		List<AccountBookDto> accDateDtoList = new ArrayList<>();
		
		for (AccountBook accountBook : accDateList) {
			
			AccountBookDto accBookDto = new AccountBookDto();
			
			String accDate = accountBook.getAccDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			accBookDto.setAccDate(accDate);
			
			accDateDtoList.add(accBookDto);
		}
		
		return accDateDtoList;
	}
	
	// TODO : main - 이번달 총 지출 금액 가져오는 repository
	
	
	// TODO : main - 오늘(now로 조회?) 지출/수입 금액 가져오는 repository
}

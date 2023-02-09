package com.account.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	// main - 날짜 그룹화해서 가져오는 repository
	public List<AccountBookDto> getAccDate(Long memberId) {
		
		List<LocalDate> accDateList = accountBookRepository.findAccDateByMemberId(memberId);
		List<AccountBookDto> accDateDtoList = new ArrayList<>();
		
		for (LocalDate date : accDateList) {
			
			AccountBookDto accBookDto = new AccountBookDto();
			
			String accDate = dateToString(date);
			accBookDto.setAccDate(accDate);
			
			accDateDtoList.add(accBookDto);
		}
		
		return accDateDtoList;
	}
	
	// TODO : main - 이번달 총 지출 금액 가져오는 repository
	
	
	// TODO : main - 오늘(now로 조회?) 지출/수입 금액 가져오는 repository
	public int getTodayMoney (Long memberId) {
		
		LocalDate nowDate = LocalDate.now();
		
		List<AccountBook> getMoney = accountBookRepository.findbyAccDateAndMemberId(dateToString(nowDate), memberId);
		
		return 0;
	}
	
	// list - 매개로 받은 날짜로 해당일 타이틀, 지출구분, 금액 가져오기
	public List<AccountBookDto> getAccList(String accDate, Long memberId) {
		
		List<AccountBookDto> accListDto = new ArrayList<>();
		List<AccountBook> accList = accountBookRepository.findbyAccDateAndMemberId(accDate, memberId);
		
		if (accList != null) {
			
			for (AccountBook accBook : accList) {
				
				AccountBookDto accBookDto = new AccountBookDto();
				
				accBookDto.setAccId(accBook.getId());
				accBookDto.setAccDate(dateToString(accBook.getAccDate()));
				accBookDto.setAccTitle(accBook.getAccTitle());
				accBookDto.setAccStatus(accBook.getAccStatus());
				accBookDto.setMoney(accBook.getMoney());
				
				accListDto.add(accBookDto);
			}
		}
		
		return accListDto;
	}
	
	// dtlList account_id를 매개로 금액, 카테고리, 타이틀, 상세타이틀 가져오기
	public AccountBookDto getDtlList(Long accId) {
		
		Optional<AccountBook> accountBook = accountBookRepository.findById(accId);
		AccountBook accBook = accountBook.orElse(new AccountBook());
		AccountBookDto accDtl = new AccountBookDto();
		
		accDtl.setAccDate(dateToString(accBook.getAccDate()));
		accDtl.setMoney(accBook.getMoney());
		accDtl.setAccTitle(accBook.getAccTitle());
		accDtl.setAccDtlMemo(accBook.getAccDtlMemo());
		accDtl.setOtherCtgName(accBook.getOtherCtgName());
		
		SubCategoryDto subCategoryDto = new SubCategoryDto();
		subCategoryDto.setId(accBook.getSubCategory().getId());
		accDtl.setSubCategoryDto(subCategoryDto);
		
		return accDtl;
	}
	
	// main / sub 카테고리 이름가져옴
	public SubCategoryDto getCtg(Long subId) {
		
		SubCategory subCategory = subCategoryRepository.findBySubId(subId);
		SubCategoryDto subCategoryDto = new SubCategoryDto();
		
		subCategoryDto.setSubCtgName(subCategory.getSubCtgName());
		
		MainCategoryDto mainCategoryDto = new MainCategoryDto();
		mainCategoryDto.setMainCtgName(subCategory.getMainCategory().getMainCtgName());
		subCategoryDto.setMainCategoryDto(mainCategoryDto);
		
		return subCategoryDto;
	}
	
	//날짜 데이터 변환 string > date
	public LocalDate stringToDate(String accDate) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate date = LocalDate.parse(accDate, formatter);
		
		return date;
	}
	
	//날짜 데이터 변환 date > string
	public String dateToString(LocalDate accDate) {
		
		String date = accDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		return date;
	}
}

package com.account.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

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
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public List<AccountBookDto> getAccDate(Long memberId) {
		
		List<AccountBook> accDateList = accountBookRepository.findByMemberId(memberId);
		List<AccountBookDto> accDateDtoList = new ArrayList<>();
		
		for (AccountBook date : accDateList) {
			
			AccountBookDto accBookDto = new AccountBookDto();
			
			String accDate = dateToString(date.getAccDate());
			accBookDto.setAccDate(accDate);
			
			accDateDtoList.add(accBookDto);
		}
		
		return accDateDtoList;
	}
	
	// main - 이번달 총 지출 금액 가져오는 repository
	@Transactional(readOnly = true)
	public Long getTotalExpend(Long memberId) {
		
		LocalDate month = LocalDate.now();
		String date = month.format(DateTimeFormatter.ofPattern("yyyy-MM"));
		
		Long totalExpend = accountBookRepository.findByMoney(date, memberId);
		
		return totalExpend;
	}
	
	
	// main - 오늘(now로 조회?) 지출/수입 금액 가져오는 repository
	@Transactional(readOnly = true)
	public List<AccountBookDto> getTodayMoney (Long memberId) {
		
		LocalDate nowDate = LocalDate.now();
		
		List<AccountBook> getMoney = accountBookRepository.findbyAccDateAndMemberId(dateToString(nowDate), memberId);
		List<AccountBookDto> money = new ArrayList<>();
		AccountBookDto statusEx = new AccountBookDto();
		AccountBookDto statusSa = new AccountBookDto();
		long ex = 0;
		long sa = 0;
		
		for (AccountBook accBook : getMoney) {
			
			
			if (accBook.getAccStatus().equals("0")) {
				
				ex += accBook.getMoney();
			} else {
				
				sa += accBook.getMoney();
			}
		}
		
		statusEx.setAccStatus("0");
		statusEx.setMoney(ex);
		money.add(statusEx);
		
		statusSa.setAccStatus("1");
		statusSa.setMoney(sa);
		money.add(statusSa);
		
		return money;
	}
	
	// list - 매개로 받은 날짜로 해당일 타이틀, 지출구분, 금액 가져오기
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public AccountBookDto getDtlList(Long accId) {
		
		Optional<AccountBook> accountBook = accountBookRepository.findById(accId);
		AccountBook accBook = accountBook.orElse(new AccountBook());
		AccountBookDto accDtl = new AccountBookDto();
		
		accDtl.setAccId(accId);
		accDtl.setAccDate(dateToString(accBook.getAccDate()));
		accDtl.setAccStatus(accBook.getAccStatus());
		accDtl.setMoney(accBook.getMoney());
		accDtl.setAccTitle(accBook.getAccTitle());
		accDtl.setAccDtlMemo(accBook.getAccDtlMemo());
		accDtl.setOtherCtgName(accBook.getOtherCtgName());
		
		accDtl.setSubCategoryDto(getCtg(accBook.getSubCategory().getId()));
		
		return accDtl;
	}
	
	// main, sub 카테고리 이름가져옴
	@Transactional(readOnly = true)
	public SubCategoryDto getCtg(Long subId) {
		
		SubCategory subCategory = subCategoryRepository.findBySubId(subId);
		SubCategoryDto subCategoryDto = new SubCategoryDto();
		
		subCategoryDto.setId(subId);
		subCategoryDto.setSubCtgName(subCategory.getSubCtgName());
		
		MainCategoryDto mainCategoryDto = new MainCategoryDto();
		mainCategoryDto.setId(subCategory.getMainCategory().getId());
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
	
	// 기입장 수정하기
	public Long updateAccBook(AccountBookDto accountBookDto) {
		
		AccountBook accountBook = accountBookRepository.findById(accountBookDto.getAccId())
													   .orElseThrow(EntityNotFoundException::new);
		accountBook.updateAccountBook(accountBookDto);
		
		return accountBookDto.getAccId();
	}

	// 기입장 삭제
	public void deleteAccBook(Long accId) {

		AccountBook accountBook = accountBookRepository.findById(accId)
													   .orElseThrow(EntityNotFoundException::new);
		accountBookRepository.delete(accountBook);
	}
	
}

package com.account.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.account.dto.AccountBookDto;
import com.account.dto.MainCategoryDto;
import com.account.dto.MemberFormDto;
import com.account.dto.SubCategoryDto;
import com.account.entity.AccountBook;
import com.account.entity.Member;
import com.account.service.AccountBookService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/accountbook")
@Controller
@RequiredArgsConstructor
public class AccBookController {

	private final AccountBookService accountBookService;
	
	// 기입장 화면
	@GetMapping(value = "/add")
	public String accBookAdd(Model model) {
		
		List<MainCategoryDto> mainCtgDtos = accountBookService.getMainCtg();
		
		model.addAttribute("mainCtgDtos", mainCtgDtos);
		model.addAttribute("accountBookDto", new AccountBookDto());
		return "accounting/accbookadd";
	}
	
	// 기입하기 눌렀을때
	@PostMapping(value = "/add")
	public String accBookForm(@Valid AccountBookDto accountBookDto, BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			
			return "accounting/accbookadd";
		}
		
		try {
			
			AccountBook accountBook = AccountBook.createAccountBook(accountBookDto);
			accountBookService.saveAccount(accountBook);
		} catch (Exception e) {
			
			model.addAttribute("errorMessage", "기입장 등록 중 에러가 발생했습니다.");
			return "accounting/accbookadd";
		}
		
		return "redirect:/";
	}
	
	// 소분류 카테고리 가져오기
		@GetMapping("/mainCtg/{mainCtgId}")
		public @ResponseBody ResponseEntity<List<SubCategoryDto>> idCheck(@PathVariable("mainCtgId") String mainCtgId) {
			
			List<SubCategoryDto> subCtg = accountBookService.getSubCtg(mainCtgId);
			
			return new ResponseEntity<List<SubCategoryDto>>(subCtg, HttpStatus.OK);
		}
	
	
	
	
	// 기입목록 화면
	@GetMapping(value = "/list")
	public String accBookList() {
		
		return "accounting/accBookList";
		
	}
	
	// 기입 상세목록 화면
	@GetMapping(value = "/dtllist")
	public String accBookDtlList() {
		
		return "accounting/accBookDtlList";
	}
}

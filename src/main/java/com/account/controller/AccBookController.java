package com.account.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.account.service.MainService;
import com.account.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/accountbook")
@Controller
@RequiredArgsConstructor
public class AccBookController {

	private final AccountBookService accountBookService;
	private final MainService mainService;
	private final MemberService memberService;
	
	// 기입장 화면
	@GetMapping(value = "/add")
	public String accBookAdd(Model model, Principal principal) {
		
		MainCategoryDto mainCtgDto = new MainCategoryDto();
		SubCategoryDto subCtgDto = new SubCategoryDto();
		AccountBookDto accBookDto = new AccountBookDto();
		subCtgDto.setMainCategoryDto(mainCtgDto);
		accBookDto.setSubCategoryDto(subCtgDto);
		
		LocalDate today = LocalDate.now();
		
		
		expendGP(model, principal.getName());
		getMainCtg(model);
		model.addAttribute("accountBookDto", accBookDto);
		model.addAttribute("today", today);
		return "accounting/accbookadd";
	}
	
	// 기입하기 눌렀을때
	@PostMapping(value = "/add")
	public String accBookForm(@Valid AccountBookDto accountBookDto, BindingResult bindingResult, Model model, Principal principal) {
		
		if (bindingResult.hasErrors()) {
			
			expendGP(model, principal.getName());
			getMainCtg(model);
			return "accounting/accbookadd";
		}
		
		String userId = principal.getName();
		
		try {
			
			Member member = memberService.getMember(userId);
			AccountBook accountBook = AccountBook.createAccountBook(accountBookDto, member);
			accountBookService.saveAccount(accountBook);
		} catch (Exception e) {
			
			model.addAttribute("errorMessage", "기입장 등록 중 에러가 발생했습니다.");
			getMainCtg(model);
			expendGP(model, principal.getName());
			return "accounting/accbookadd";
		}
		
		return "redirect:/";
	}
	
	// 소분류 카테고리 가져오기
	@GetMapping("/mainCtg/{mainCtgId}")
	public @ResponseBody ResponseEntity<List<SubCategoryDto>> getSubCtg(@PathVariable("mainCtgId") String mainCtgId) {
		
		List<SubCategoryDto> subCtg = accountBookService.getSubCtg(mainCtgId);
		
		return new ResponseEntity<List<SubCategoryDto>>(subCtg, HttpStatus.OK);
	}
	
	// 대분류 카테고리 가져오기
	public Model getMainCtg(Model model) {
		
		List<MainCategoryDto> mainCtgDtos = accountBookService.getMainCtg();
		
		return model.addAttribute("mainCtgDtos", mainCtgDtos);
	}
	
	// 기입목록 화면
	@GetMapping(value = {"/list/{accDate}"})
	public String accBookList(@PathVariable("accDate") String accDate, Model model, Principal principal) {
		
		Member member = memberService.getMember(principal.getName());
		
		List<AccountBookDto> accList = accountBookService.getAccList(accDate, member.getId());
		
			
		if (accList.size() == 0) {
			
			getMainCtg(model);
			return "redirect:/accountbook/add";
		}
		
		expendGP(model, principal.getName());
		model.addAttribute("accDate", accDate);
		model.addAttribute("accList", accList);
		return "accounting/accBookList";	
	}
	
	// 기입 상세목록 화면
	@GetMapping(value = "/dtllist/{accId}")
	public String accBookDtlList(@PathVariable("accId") Long accId, Model model, Principal principal) {
		
		AccountBookDto accDtl = accountBookService.getDtlList(accId);
		SubCategoryDto ctg = accountBookService.getCtg(accDtl.getSubCategoryDto().getId());
		
		expendGP(model, principal.getName());
		model.addAttribute("accDtl", accDtl);
		model.addAttribute("ctg", ctg);
		return "accounting/accBookDtlList";
	}
	
	// 기입 수정 화면
	@GetMapping(value = "/modify/{accId}")
	public String accBookModify(@PathVariable("accId") Long accId, Model model, Principal principal) {
		
		try {
			
			getMainCtg(model);
			AccountBookDto accountBookDto = accountBookService.getDtlList(accId);
			String date = accountBookDto.getAccDate();
			LocalDate today = LocalDate.now();
			
			expendGP(model, principal.getName());
			model.addAttribute("today", today);
			model.addAttribute("date", date);
			model.addAttribute("accountBookDto", accountBookDto);
		} catch (Exception e) {
			
			expendGP(model, principal.getName());
			model.addAttribute("errorMessage", "존재하지 않는 가계부입니다.");
			model.addAttribute("accountBookDto", new AccountBookDto());
			return "accounting/accbookadd";
		}
		
		return "accounting/accbookadd";
	}
	
	// 기입 수정 클릭했을 때 화면
	@PostMapping(value = "/modify/{accId}")
	public String accBookUpdate(@Valid AccountBookDto accountBookDto, BindingResult bindingResult, Model model, Principal principal) {

		if (bindingResult.hasErrors()) {
			
			expendGP(model, principal.getName());
			getMainCtg(model);
			return "accounting/accbookadd";
		}
		
		try {
			
			accountBookService.updateAccBook(accountBookDto);
		} catch (Exception e) {
			
			expendGP(model, principal.getName());
			model.addAttribute("errorMessage", "기입장 수정 중 에러가 발생하였습니다.");
			return "accounting/accbookadd";
		}
		
		String accIc = Long.toString(accountBookDto.getAccId());
		
		return "redirect:/accountbook/dtllist/" + accIc;
	}
	
	@DeleteMapping(value = "/dtllist/{accId}/delete")
	public @ResponseBody ResponseEntity deleteAccBook(@PathVariable("accId") Long accId, Principal principal) {
		
		accountBookService.deleteAccBook(accId);
		return new ResponseEntity<Long>(accId, HttpStatus.OK);
	}
	
	public Model expendGP (Model model, String userId) {
		
		MemberFormDto memberFormDto = mainService.getMember(userId);
		String expendP = mainService.getExpendP(memberFormDto.getMemberId(), memberFormDto.getTargetExpend());
		
		return model.addAttribute("expendP", expendP);
	}
}

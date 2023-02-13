package com.account.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.account.dto.AccountBookDto;
import com.account.dto.AccountBookSearchDto;
import com.account.dto.MainCategoryDto;
import com.account.service.AccountBookService;
import com.account.service.GraphService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/graph")
@Controller
@RequiredArgsConstructor
public class GraphController {

	private final AccountBookService accountBookService;
	private final GraphService graphService;
	
	// 가계부 조회 화면
	@GetMapping(value = "/result")
	public String result(Model model) {
		
		getMainCtg(model);
		model.addAttribute("AccountBookSearchDto", new AccountBookSearchDto());
		return "graph/result";
	}
	
	// 가계부 조회 화면
	@PostMapping(value = "/result")
	public String resultSearch(AccountBookSearchDto accountBookSearchDto, Model model, Principal principal) {
		
		AccountBookSearchDto accBookSearchDto =  new AccountBookSearchDto();
		accBookSearchDto = accountBookSearchDto;
		List<AccountBookDto> AccountBookDto = graphService.getSearchAccBook(accountBookSearchDto, principal.getName());
		
		getMainCtg(model);
		model.addAttribute("AccountBookDto", AccountBookDto);
		model.addAttribute("AccountBookSearchDto", accBookSearchDto);
		return "graph/result";
	}
	
	// 그래프 조회 화면
	@GetMapping(value = "/pieGp")
	public String resultGraph(Model model) {
		
		model.addAttribute("AccountBookSearchDto", new AccountBookSearchDto());
		return "graph/resultGraph";
	}
	
	// 가계부 조회 화면
	@PostMapping(value = "/pieGp")
	public String resultGraph(AccountBookSearchDto accountBookSearchDto, Model model, Principal principal) {
		
		AccountBookSearchDto accBookSearchDto =  new AccountBookSearchDto();
		accBookSearchDto = accountBookSearchDto;
		List<AccountBookDto> AccountBookDto = graphService.getSearchAccBook(accountBookSearchDto, principal.getName());
		
		model.addAttribute("AccountBookDto", AccountBookDto);
		model.addAttribute("AccountBookSearchDto", accBookSearchDto);
		return "graph/result";
	}
	
	// 대분류 카테고리 가져오기
	public Model getMainCtg(Model model) {
		
		List<MainCategoryDto> mainCtgDtos = accountBookService.getMainCtg();
		
		return model.addAttribute("mainCtgDtos", mainCtgDtos);
	}
}

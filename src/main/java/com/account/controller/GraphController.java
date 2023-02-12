package com.account.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.account.dto.AccountBookDto;
import com.account.dto.AccountBookSearchDto;
import com.account.dto.MainCategoryDto;
import com.account.service.AccountBookService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/graph")
@Controller
@RequiredArgsConstructor
public class GraphController {

	private final AccountBookService accountBookService;
	
	// 가계부 조회 화면
	@GetMapping(value = "/result")
	public String result(Model model) {
		
		List<MainCategoryDto> mainCtgDtos = accountBookService.getMainCtg();
		
		model.addAttribute("mainCtgDtos", mainCtgDtos);
		model.addAttribute("AccountBookSearchDto", new AccountBookSearchDto());
		
		return "graph/result";
	}
	
	// 가계부 조회 화면
	@PostMapping(value = "/result")
	public String resultSearch(AccountBookSearchDto accountBookSearchDto, Model model) {
		
		
		model.addAttribute("accountBookSearchDto", accountBookSearchDto);
		return "graph/result";
	}
	
	// 그래프 조회 화면
	@GetMapping(value = "/graph")
	public String resultGraph() {
		
		return "graph/resultGraph";
	}
}

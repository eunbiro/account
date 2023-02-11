package com.account.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		return "graph/result";
	}
	
	// 그래프 조회 화면
	@GetMapping(value = "/graph")
	public String resultGraph() {
		
		return "graph/resultGraph";
	}
}

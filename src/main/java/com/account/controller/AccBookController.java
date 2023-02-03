package com.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/accountbook")
@Controller
@RequiredArgsConstructor
public class AccBookController {

	// 기입장 화면
	@GetMapping(value = "/add")
	public String accBookAdd(Model model) {
		
		return "accounting/accbookadd";
	}
	
	// 기입목록 화면
	@GetMapping(value = "/list")
	public String accBookList(Model model) {
		
		return "accounting/accBookLlist";
		
	}
	
	// 기입 상세목록 화면
	@GetMapping(value = "/dtllist")
	public String accBookDtlList(Model model) {
		
		return "accounting/accBookDtlList";
	}
}

package com.account.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.account.dto.AccountBookDto;
import com.account.dto.MemberFormDto;
import com.account.service.AccountBookService;
import com.account.service.MainService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final MainService mainService;
	private final AccountBookService accountBookService;
	
	@GetMapping(value = "/")
	public String main(Model model, Principal principal) {
		
		MemberFormDto memberFormDto = mainService.getMember(principal.getName());
//		List<AccountBookDto> accDate = accountBookService.getAccDate(principal.getName());
		
//		model.addAttribute("accDate", accDate);
		model.addAttribute("memberFormDto", memberFormDto);
		return "main";
	}
}

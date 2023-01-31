package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

	// 회원가입 화면
	@GetMapping(value = "/new")
	public String memberForm(Model model) {
		
		return "member/memberForm";
	}
	
	@GetMapping(value = "/login")
	public String loginMember() {
		
		return "member/memberLoginForm";
	}
}

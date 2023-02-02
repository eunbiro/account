package com.example.controller;

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
	public String memberForm(Model model) {
		
		return "accounting/accbookadd";
	}
}

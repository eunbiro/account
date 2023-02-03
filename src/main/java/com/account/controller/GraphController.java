package com.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/graph")
@Controller
@RequiredArgsConstructor
public class GraphController {

	// 가계부 조회 화면
	@GetMapping(value = "/result")
	public String result(Model model) {
		
		return "graph/result";
	}
	
	// 그래프 조회 화면
	@GetMapping(value = "/graph")
	public String resultGraph(Model model) {
		
		return "graph/resultGraph";
	}
}

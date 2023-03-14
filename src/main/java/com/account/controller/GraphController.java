package com.account.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
	
	// 그래프 조회 화면
	@PostMapping(value = "/pieGp")
	public String resultGraph(AccountBookSearchDto accountBookSearchDto, Model model, Principal principal) {
		
		AccountBookSearchDto accBookSearchDto =  new AccountBookSearchDto();
		accBookSearchDto = accountBookSearchDto;
		List<AccountBookSearchDto> searchDtoList = graphService.getSearchGraph(accountBookSearchDto, principal.getName());
		
		model.addAttribute("searchDtoList", searchDtoList);
		model.addAttribute("AccountBookSearchDto", accBookSearchDto);
		return "graph/resultGraph";
	}
	
	// 대분류 카테고리 가져오기
	public Model getMainCtg(Model model) {
		
		List<MainCategoryDto> mainCtgDtos = accountBookService.getMainCtg();
		
		return model.addAttribute("mainCtgDtos", mainCtgDtos);
	}
	
	// 엑셀 다운로드
	@PostMapping(value = "/result/exceldownload")
	public void resultExcel(AccountBookSearchDto accountBookSearchDto, HttpServletResponse response, Principal principal) throws IOException {
		
		List<AccountBookDto> AccountBookDtoList = graphService.getSearchAccBook(accountBookSearchDto, principal.getName());
		
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("가계부 정보");
		int rowNo = 0;
		
		Row headerRow = sheet.createRow(rowNo++);
        headerRow.createCell(0).setCellValue("날짜");
        headerRow.createCell(1).setCellValue("내용");
        headerRow.createCell(2).setCellValue("카테고리");
        headerRow.createCell(3).setCellValue("금액");
        
        for (AccountBookDto accountBookDto : AccountBookDtoList) {
        	
        	Row row = sheet.createRow(rowNo++);
        	row.createCell(0).setCellValue(accountBookDto.getAccDate());
        	row.createCell(1).setCellValue(accountBookDto.getAccTitle());
        	row.createCell(2).setCellValue(accountBookDto.getSubCategoryDto().getMainCategoryDto().getMainCtgName());
        	row.createCell(3).setCellValue(accountBookDto.getMoney());
        }
        
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=accountList.xls");

        workbook.write(response.getOutputStream());
        workbook.close();
	}
}

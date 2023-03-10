package com.account.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountBookDto {

	private Long accId;
	
	private String accDate;			// 입력날짜
	
	@NotNull(message = "수입/지출은 필수 입력 값입니다.")
	private String accStatus;				// 수입지출
	
	@NotNull(message = "금액은 필수 입력 값입니다.")
	private Long money;						// 입력금액
	
	@NotNull(message = "카테고리는 필수 입력 값입니다.")
	private SubCategoryDto subCategoryDto;	// 카테고리
	
	private String otherCtgName;			// 카테고리 직접입력
	
	@NotNull(message = "타이틀은 필수 입력 값입니다.")
	private String accTitle;				// 금액출처
	
	private String accDtlMemo;				// 상세내용
}

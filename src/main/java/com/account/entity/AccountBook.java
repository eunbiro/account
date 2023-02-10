package com.account.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.account.dto.AccountBookDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "accounts")	// 테이블명 (설정안하면 클래스이름으로 설정됨)
@Getter
@Setter
@ToString
public class AccountBook extends BaseTimeEntity {

	@Id
	@Column(name = "account_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 가계부 아이디
	
	@Column(nullable = false)
	private Long money;						// 입력금액
	
	@Column(nullable = false)
	private LocalDate accDate;			// 입력날짜
	
	@Column(nullable = false)
	private String accStatus;				// 수입지출
	
	@Column(length = 50)
	private String otherCtgName;			// 카테고리 직접입력
	
	@Column(nullable = false)
	private String accTitle;				// 금액출처
	
	@Lob
	private String accDtlMemo;				// 상세내용
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;					// 멤버아이디 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_ctg_id")
	private SubCategory subCategory;		// 카테고리
	
	public static AccountBook createAccountBook(AccountBookDto accountBookDto, Member member) {
		
		AccountBook accountBook = new AccountBook();
		
		accountBook.setAccStatus(accountBookDto.getAccStatus());
		accountBook.setMoney(accountBookDto.getMoney());
		accountBook.setOtherCtgName(accountBookDto.getOtherCtgName());
		accountBook.setAccTitle(accountBookDto.getAccTitle());
		accountBook.setAccDtlMemo(accountBookDto.getAccDtlMemo());
		
		SubCategory subCtg = new SubCategory();
		subCtg.setId(accountBookDto.getSubCategoryDto().getId());
		
		accountBook.setSubCategory(subCtg);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(accountBookDto.getAccDate(), formatter);
		accountBook.setAccDate(date);
		
		accountBook.setMember(member);
		
		return accountBook;
	}
	
	public void updateAccountBook(AccountBookDto accountBookDto) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(accountBookDto.getAccDate(), formatter);
		
		this.accDate = date;
		this.accStatus = accountBookDto.getAccStatus();
		this.money = accountBookDto.getMoney();
		this.otherCtgName = accountBookDto.getOtherCtgName();
		this.accTitle = accountBookDto.getAccTitle();
		this.accDtlMemo = accountBookDto.getAccDtlMemo();
		this.subCategory.setId(accountBookDto.getSubCategoryDto().getId());
	}
	
}

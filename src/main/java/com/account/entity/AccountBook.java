package com.account.entity;

import java.time.LocalDateTime;

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
import javax.validation.constraints.NotNull;

import com.account.dto.AccountBookDto;
import com.account.dto.SubCategoryDto;

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
	private int money;						// 입력금액
	
	@Column(nullable = false)
	private LocalDateTime accDate;			// 입력날짜
	
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
	
	public static AccountBook createAccountBook(AccountBookDto accountBookDto) {
		
		AccountBook accountBook = new AccountBook();
		accountBook.setAccDate(accountBookDto.getAccDate());
		accountBook.setAccStatus(accountBookDto.getAccStatus());
		accountBook.setMoney(accountBookDto.getMoney());
		accountBook.setOtherCtgName(accountBookDto.getOtherCtgName());
		accountBook.setAccTitle(accountBookDto.getAccTitle());
		accountBook.setAccDtlMemo(accountBookDto.getAccDtlMemo());
		
		accountBook.subCategory.setId(accountBookDto.getSubCategoryDto().getId());
		
		return accountBook;
	}
	
}

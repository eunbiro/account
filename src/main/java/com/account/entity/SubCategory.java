package com.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "sub_category")	// 테이블명 (설정안하면 클래스이름으로 설정됨)
@Getter
@Setter
@ToString
public class SubCategory {

	@Id
	@Column(name = "sub_ctg_id")
	private Long id;
	
	@Column(length = 50)
	private String subCtgName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "main_ctg_id")
	private MainCategory mainCategory;
}

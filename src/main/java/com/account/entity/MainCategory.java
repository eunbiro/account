package com.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "main_category")	// 테이블명 (설정안하면 클래스이름으로 설정됨)
@Getter
@Setter
@ToString
public class MainCategory {

	@Id
	@Column(name = "main_ctg_id")
	private Long id;
	
	@Column(length = 50)
	private String mainCtgName;
}

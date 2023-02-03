package com.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {

	private String userId;
	
	private String password;
	
	private String nickname;
	
	private int targetExpend;
	
	private int targetSaving;
}

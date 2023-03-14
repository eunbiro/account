package com.account.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.account.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {

	@NotBlank(message = "아이디는 필수 입력 값 입니다.")
	@Length(min = 4, max = 16, message = "아이디는 4자 이상, 16자 이하로 입력해주세요.")
	private String userId;

	@NotEmpty(message = "비밀번호는 필수 입력 값 입니다.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
	private String password;
	
	@NotEmpty(message = "닉네임은 필수 입력 값 입니다.")
	@Length(min = 2, max = 12, message = "닉네임은 2자 이상, 12자 이하로 입력해주세요.")
	private String nickname;
	
	private String email;
	
	private String targetExpend;
	
	private String targetSaving;
	
	private Long memberId;
	
	public void creatMemberFormDto(Member member) {
		
		this.memberId = member.getId();
		this.userId = member.getUserId();
		this.email = member.getEmail();
		this.nickname = member.getNickname();
		this.targetExpend = member.getTargetExpend();
		this.targetSaving = member.getTargetSaving();
	}
}

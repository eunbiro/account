package com.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.account.dto.MemberFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")	// 테이블명 (설정안하면 클래스이름으로 설정됨)
@Getter
@Setter
@ToString
public class Member {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length = 16, nullable = false)
	private String userId;
	
	@Column(nullable = false)
	private String password;
	
	@Column(length = 25, nullable = false)
	private String nickname;
	
	private int targetExpend;
	
	private int targetSaving;
	
	private String role;
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		
		Member member = new Member();
		member.setUserId(memberFormDto.getUserId());
		member.setNickname(memberFormDto.getNickname());
		member.setRole("USER");
		
		if (memberFormDto.getTargetExpend() == 0) {
			
			member.setTargetExpend(500000);
		} else {
			
			member.setTargetExpend(memberFormDto.getTargetExpend());
		}
		
		if (memberFormDto.getTargetSaving() == 0) {
			
			member.setTargetSaving(500000);
		} else {
			
			member.setTargetSaving(memberFormDto.getTargetSaving());
		}
		
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		
		return member;
	}
	
	public void updateMember(MemberFormDto memberFormDto) {
		
		this.nickname = memberFormDto.getNickname();
		this.targetExpend = memberFormDto.getTargetExpend();
		this.targetSaving = memberFormDto.getTargetSaving();
	}
}

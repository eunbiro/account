package com.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.account.dto.MemberFormDto;

import lombok.Builder;
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
	
	@Column(nullable = false)
	private String email;
	
	private String targetExpend;
	
	private String targetSaving;
	
	private String role;
	
	private String provider;    // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지
	
    private String providerId;  // oauth2를 이용할 경우 아이디값
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		
		Member member = new Member();
		member.setUserId(memberFormDto.getUserId());
		member.setNickname(memberFormDto.getNickname());
		member.setRole("USER");
		member.setEmail(memberFormDto.getEmail());
		
		if (memberFormDto.getTargetExpend().isEmpty()) {
			
			member.setTargetExpend("500000");
		} else {
			
			member.setTargetExpend(memberFormDto.getTargetExpend());
		}
		
		if (memberFormDto.getTargetSaving().isEmpty()) {
			
			member.setTargetSaving("500000");
		} else {
			
			member.setTargetSaving(memberFormDto.getTargetSaving());
		}
		
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		
		return member;
	}
	
	public Member() {};
	
	@Builder(builderClassName = "OAuth2Register", builderMethodName = "OAuth2Register")
    public Member(String username, String password, String email, String role, String provider, String providerId) {
        this.userId = username;
        this.password = password;
        this.nickname = "없음";
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.targetExpend = "0";
        this.targetSaving = "0";
    }
	
	public void updateMember(MemberFormDto memberFormDto) {
		
		this.nickname = memberFormDto.getNickname();
		this.targetExpend = memberFormDto.getTargetExpend();
		this.targetSaving = memberFormDto.getTargetSaving();
	}
}

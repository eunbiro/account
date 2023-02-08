package com.account.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.account.config.AuditorAwareImpl;
import com.account.dto.MemberFormDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MainService {

	private final MemberService memberService;
	
	public MemberFormDto getMember(String userId) {
		
		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.creatMemberFormDto(memberService.getMember(userId));
		
		return memberFormDto;
	}
}

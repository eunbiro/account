package com.account.service;

import java.text.DecimalFormat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.account.dto.MemberFormDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MainService {

	private final MemberService memberService;
	private final AccountBookService accountBookService;
	
	@Transactional(readOnly = true)
	public MemberFormDto getMember(String userId) {
		
		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.creatMemberFormDto(memberService.getMember(userId));
		
		return memberFormDto;
	}
	
	/*
	@Transactional(readOnly = true)
	public String getExpendP(Long memberId, String expend) {
		DecimalFormat formatter = new DecimalFormat("##,###,###.##%");
		
		Long totalExpend = accountBookService.getTotalExpend(memberId);
		String chk;
		
		if (totalExpend == null) {
					
			float money = ((float)0/expend);
			chk = formatter.format(money);
		} else {
			
			float money = ((float)totalExpend/expend);
			chk = formatter.format(money);
		}
		return chk;
	}
	*/
}

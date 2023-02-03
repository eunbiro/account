package com.account.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.account.dto.MemberFormDto;
import com.account.entity.Member;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

	@Autowired
	MemberService memberService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Member createMember() {
		
		MemberFormDto member = new MemberFormDto();
		member.setUserId("umbi");
		member.setPassword("1234");
		member.setNickname("음비");
		member.setTargetExpend(500000);
		member.setTargetSaving(300000);
		
		return Member.createMember(member, passwordEncoder);
	}
	
//	@Test
//	@DisplayName("회원가입 테스트")
//	public void saveMembertest() {
//		
//		Member member = createMember();
//		Member savedMember = memberService.saveMember(member);
//		
//		assertEquals(member.getUserId(), savedMember.getUserId());
//		assertEquals(member.getPassword(), savedMember.getPassword());
//		assertEquals(member.getNickname(), savedMember.getNickname());
//		assertEquals(member.getTargetExpend(), savedMember.getTargetExpend());
//		assertEquals(member.getTargetSaving(), savedMember.getTargetSaving());
//	}
	
	@Test
	@DisplayName("중복 회원 가입 테스트")
	public void saveDuplicateMemberTest() {
		
		Member member1 = createMember();
		Member member2 = createMember();
		
		memberService.saveMember(member1);
		
		Throwable e = assertThrows(IllegalStateException.class, ()-> {
			
			memberService.saveMember(member2);
		});
		
		assertEquals("이미 가입된 회원입니다.", e.getMessage());
	}
}

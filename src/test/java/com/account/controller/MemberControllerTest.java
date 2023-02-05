package com.account.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.account.dto.MemberFormDto;
import com.account.entity.Member;
import com.account.service.MemberService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberControllerTest {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Member createMember(String userId, String password) {

		MemberFormDto member = new MemberFormDto();
		member.setUserId("umbi");
		member.setPassword("1234");
		member.setNickname("음비");
		member.setTargetExpend(500000);
		member.setTargetSaving(300000);

		Member member2 = Member.createMember(member, passwordEncoder);
		return memberService.saveMember(member2);
	}
	
	@Test
	@DisplayName("로그인 성공 테스트")
	public void loginSuccessTest() throws Exception {
		String userId = "umbi";
		String password = "1234";
		
		this.createMember(userId, password);
		
		mockMvc.perform(formLogin().userParameter("userId")
				.loginProcessingUrl("/members/login")
				.user(userId).password(password))
				.andExpect(SecurityMockMvcResultMatchers.authenticated());
	}
	
	@Test
	@DisplayName("로그인 실패 테스트")
	public void loginFailTest() throws Exception {
		String userId = "umbi";
		String password = "1234";
		
		this.createMember(userId, password);
		
		mockMvc.perform(formLogin().userParameter("userId")
		.loginProcessingUrl("/members/login")						// 회원가입 메소드를 실행 후에 회원정보로 로그인이 되는지 테스트를 진행(해당 url로 로그인 요청)
		.user(userId).password("12345"))
		.andExpect(SecurityMockMvcResultMatchers.unauthenticated());	// 로그인이 성공해서 인증되면 테스트 코드를 통과시킨다.
	}
	
}

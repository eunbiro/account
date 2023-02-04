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
	
}

package com.account.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.account.entity.Member;
import com.account.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Member member = memberRepository.findByUserId(userId);
		
		if (member == null) {
			
			throw new UsernameNotFoundException(userId);
		}
		
		return User.builder()
				.username(member.getUserId())
				.password(member.getPassword())
				.roles("USER")
				.build();
	}
	
	public Member saveMember(Member member) {
		
		vaildateDuplicateMember(member);
		return memberRepository.save(member);
	}
	
	private void vaildateDuplicateMember(Member member) {
		
		Member findMember = memberRepository.findByUserId(member.getUserId());
		
		if (findMember != null) {
			
			throw new IllegalStateException("중복된 아이디입니다.");
		}
	}
	
	public Member vaildateDuplicateId(String userId) {
		
		return memberRepository.findByUserId(userId);
	}
}

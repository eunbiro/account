package com.account.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.account.dto.MemberFormDto;
import com.account.entity.AccountBook;
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
	
	// 멤버 가져옴
	@Transactional(readOnly = true)
	public Member getMember(String userId) {
		
		Member member = memberRepository.findByUserId(userId);
		return member;
	}
	
	// 멤버 여부확인
	@Transactional(readOnly = true)
	public int vaildateDuplicateId(String userId) {
		
		Member member = memberRepository.findByUserId(userId);
		int chk;
		
		if (member == null) {
			
			return chk = 0;
		}
		
		return chk = 1;
	}
	
	// 마이페이지 정보가져옴
	@Transactional(readOnly = true)
	public MemberFormDto getMemberDto(String userId) {
		
		MemberFormDto memberFormDto = new MemberFormDto();
		Member member = getMember(userId);
		
		memberFormDto.setMemberId(member.getId());
		memberFormDto.setNickname(member.getNickname());
		memberFormDto.setTargetExpend(member.getTargetExpend());
		memberFormDto.setTargetSaving(member.getTargetSaving());

		return memberFormDto;
	}
	
	// 회원정보 수정
	public Long updateMember(MemberFormDto memberFormDto) {
		
		Member member = memberRepository.findById(memberFormDto.getMemberId())
										.orElseThrow(EntityNotFoundException::new);
		
		member.updateMember(memberFormDto);
		
		return memberFormDto.getMemberId();
	}
	
	// 회원탈퇴
	public void deleteMember(Long memberId) {

		Member member = memberRepository.findById(memberId)
													   .orElseThrow(EntityNotFoundException::new);
		
		memberRepository.delete(member);
	}
	
}

package com.account.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.account.entity.Member;
import com.account.repository.MemberRepository;

@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Member byUserId = memberRepository.findByUserId(username);
		if(byUserId != null) {
			
			return new PrincipalDetails(byUserId);
		}
		
		return null;
	}
	
	
}

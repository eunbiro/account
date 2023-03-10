package com.account.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.account.auth.PrincipalOauth2UserService;
import com.account.service.MemberService;


@Configuration		// 스프링에서 설정 클래스로 사용하겠다.
@EnableWebSecurity	// SpringSecurityFilterChain이 자동으로 포함됨
public class SecurityConfig {

	@Autowired
	MemberService memberService;
	
	@Autowired
	PrincipalOauth2UserService pricipalOauth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		// 로그인에 대한 설정
		http.formLogin()
			.loginPage("/members/login")		// 로그인 페이지 url설정
			.defaultSuccessUrl("/")				// 로그인 성공 시 이동 할 페이지
			.usernameParameter("userId")		// 로그인 시 사용 할 파라메터 이름
			.passwordParameter("password")
			.loginProcessingUrl("/members/login")
			.failureUrl("/members/login/error")	// 로그인 실패 시 이동 할 url
		.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))	// 로그아웃 url
			.logoutSuccessUrl("/members/login")				// 로그아웃 성공 시 이동 할 url
		.and()
			.oauth2Login()
			.loginPage("/members/login")
			.defaultSuccessUrl("/")
			.failureUrl("/members/login")
			.userInfoEndpoint()
			.userService(pricipalOauth2UserService);
		
		// 페이지의 접근에 관한 설정
		http.authorizeRequests()
			.mvcMatchers("/css/**", "/js/**", "/img/**").permitAll()
			.mvcMatchers("/members/**", "/images/**").permitAll()
			.anyRequest().authenticated();				// 그 외의 페이지는 모두 로그인(인증)을 받아야 한다.
		
		
		// 인증되지 않은 사용자가 리소스(페이지, 이미지 등..)에 접근했을 때 설정
		http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
		
		return http.build();
	}
	
//	@Bean		// @Configuration 얘랑 주로 같이 사용하고 싱글톤이 보장된다.
//	public PasswordEncoder passwordEncoder() { // 비밀번호 암호화를 위해서 사용하는 빈(Bean)
//		return new BCryptPasswordEncoder();
//	}
	
	
}

package com.account.controller;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.account.auth.PrincipalDetails;
import com.account.dto.MemberFormDto;
import com.account.entity.Member;
import com.account.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;

	// 회원가입 화면
	@GetMapping(value = "/new")
	public String memberForm(Model model) {
		
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}
	
	// 회원가입 눌렀을 때 작동하는 메소드
	@PostMapping(value = "/new")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		
		// 에러가 있다면 회원가입 페이지로 이동
		if (bindingResult.hasErrors()) {
			
			return "member/memberForm";
		}
		
		try {
			
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		
		return "redirect:/members/login";
	}
	
	// 아이디 중복체크
	@GetMapping("/login/{userId}")
	public @ResponseBody ResponseEntity<Integer> idCheck(@PathVariable("userId") String userId) {
		
		Integer result = memberService.vaildateDuplicateId(userId);
		
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	// 로그인 화면
	@GetMapping(value = "/login")
	public String loginMember() {
		
		return "member/memberLoginForm";
	}

	@GetMapping(value = "/login/error")
	public String loginError(Model model) {
		
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "member/memberLoginForm";
	}
	
	// OAuth 로그인
	@GetMapping(value = "/form/logininfo")
	public String formLoginInfo(Authentication authentication, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
		Member member = principal.getMember();
		System.out.println(member);
		
		Member member2 = principalDetails.getMember();
		System.out.println(member2);
		
		return member.toString();
	}
	
	// OAuth 로그인
	@GetMapping(value = "/logininfo")
	public String loginInfo(Authentication authentication, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		String result = "";
		
		PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
		
		if (principal.getMember().getProvider() == null) {
			
			result = result + "Form로그인 : " + principal;
		} else {
			
			result = result + "Oauth2 로그인 : " + principal;
		}
		
		return result.toString();
	}
	
	// api
	@GetMapping(value = "/oauth/logininfo")
	public String oauthLoginInfo(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2UserPrincipal) {
		
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		Map<String, Object> attributes = oAuth2User.getAttributes();
		System.out.println(attributes);
		
		Map<String, Object> attributes2 = oAuth2UserPrincipal.getAttributes();
		
		return attributes.toString();
	}

	// 마이페이지 화면
	@GetMapping(value = "/mypage")
	public String myPageForm(Model model, Principal principal) {
		
		MemberFormDto memberFormDto = memberService.getMemberDto(principal.getName());
		
		model.addAttribute("memberFormDto",memberFormDto);
		return "member/memberMyPage";
	}
	
	// 마이페이지 수정 눌렀을 때 작동하는 메소드
	@PostMapping(value = "/mypage")
	public String myPageForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		
		try {

			Long memberId = memberService.updateMember(memberFormDto);
		} catch (IllegalStateException e) {
			
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberMyPage";
		}
		
		return "redirect:/";
	}
	
	@DeleteMapping(value = "/mypage/{memberId}/delete")
	public @ResponseBody ResponseEntity deleteMember(@PathVariable("memberId") Long memberId, Principal principal) {
		
		memberService.deleteMember(memberId);
		
		SecurityContextHolder.clearContext();
		return new ResponseEntity<Long>(memberId, HttpStatus.OK);
	}
	
//	@PostMapping(value = "/login2")
	public String loginMember2(HttpServletResponse response, HttpSession session, @RequestParam String userID) {
		
		Cookie idCookie = new Cookie("userCookieId", userID);
		response.addCookie(idCookie);
		
		return "member/memberLoginForm";
	}
}

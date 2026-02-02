package com.green.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
	
	@Autowired
	MemberService memberservice;
	
	//회원가입 폼
	@GetMapping("/signup")
	public String signup() {
		System.out.println("컨트롤러 signup()");
		String nextPage = "memberSignup";
		return nextPage;
	}
	//회원가입 처리
	@PostMapping("/signupresult")
	public String signupresult() {
		System.out.println("컨트롤러 signupresult()");
		return "memberSignupresult";
	}
	
	
}

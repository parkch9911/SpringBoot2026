package com.green;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// @RestController 는 @Controller + @ResponseBody 를 합친
// 어노테이션이다. => 컨트롤러 역할 + 데이터를 JSON으로 응답하여 사용하겠다
// @ResponseBody 는 메소드가 변환하는 데이터를 HTML 뷰를 찾는 용도가
// 				   	아니라, 데이터 그 자체(JSON)로 응답 받아 직접 쓰겠다.
// @RestController 하나만 맨 위에 적어주면 모든 메소드들은
// @ResponseBody를 붙이지 않아도 된다.

import com.green.carproduct.CarProductDTO;
import com.green.carproduct.CarProductService;
import com.green.member.MemberDTO;
import com.green.member.MemberService;

import jakarta.servlet.http.HttpSession;



@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	CarProductService carProductservice; //carList메소드
	
	@Autowired
	MemberService memberservice;
	
	//자동차 리스트를 JSON으로 변환하는 API
	@GetMapping("/cars")
	public List<CarProductDTO> getCarList(){
		System.out.println("api컨트롤러 자동차 리스트 요청됨...");
		// DB에서 데이터를 가져와서 그대로 리턴 (Spring이 자동으로 JSON 배열로 변환)
		// [{}]
		return carProductservice.getAllCarProduct();
	}
	
	//회원가입 API(POST)방식
	// @RequestBody 리액트에서 넘어온 JSON 데이터를
	// ->> 자바 객체 MemberDTO 에 담을 수 있게끔 변환시켜준다.
	@PostMapping("/member/signup") 
	public int signup(@RequestBody MemberDTO mdto) {
		System.out.println("api컨트롤러 signup 요청됨...");
		return memberservice.signupConfirm(mdto);
	}
	
	//로그인 메서드
	@PostMapping("/member/login")
	public MemberDTO login(@RequestBody MemberDTO mdto, HttpSession session) {
		System.out.println("api컨트롤러 login 요청됨...");
		//loginUser = {no:~,id:~,pw:~,mail:~,phone:~ ,~~}
		MemberDTO loginUser = memberservice.loginConfirm(mdto);
		if(loginUser != null) {
			session.setAttribute("loginUser", loginUser.getId());
		}
		//React로 JSON 변환		
		return loginUser;
	}
	
	//로그아웃 
	@GetMapping("/member/logout")
	public int logout(HttpSession session) {
		System.out.println("api컨트롤러 logout 요청됨...");
		session.invalidate(); //세션 삭제
		return 1; //성공 신호
	}
	
	// 한사람의 개인정보를 조회
	// select 
	@GetMapping("/member/myinfo")
	public MemberDTO myInfo(HttpSession session) {
		System.out.println("api컨트롤러 myinfo 요청됨...");
		//세션에서 로그인 사용자 꺼내기
		//다운캐스팅한다. 
		String loginId = (String) session.getAttribute("loginUser");
		//로그인 안되어있으면
		if(loginId == null) {
			return null;
		}
		//로그인 되어있으면 DB조회
		return memberservice.oneSelect(loginId);
	}
	
	//한사람 개인정보 삭제 컨트롤러
	//삭제하다 - > DeleteMapping()
	@DeleteMapping("/member/delete")
	public int delete(HttpSession session) {
		String loginId = (String) session.getAttribute("loginUser");
		if(loginId == null) {
			return 0;
		}
		//삭제 서비스 메소드 삭제성공 =1(true) 실패=0(false)
		boolean result = memberservice.oneDelete(loginId);
		if(result) {
			//로그아웃 //세션삭제
			session.invalidate();
			return 1;
		}else {
			return 0;
		}
	}

	
}

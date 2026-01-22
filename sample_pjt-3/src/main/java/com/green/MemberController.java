package com.green;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {
	
	// DI(의존성 객체 주입)   Dependency Injection
	// @Aurowired => MemberController에서 MemberService를 직접 생성하지 않고
	// 스프링 컨테이너(AppConfig)가 만든 MemberService를 주입시켜라
	@Autowired
	MemberService memberService;

	// 아래 작성한 메소드들은 핸들러 메소드들이다.
	//회원 가입양식
	//http://localhost:8090/member/signup 의 경로를 매핑(=연결)
	@GetMapping("/member/signup") 
	public String signUpForm() {
		//아래의 프린트문은 log역할
		System.out.println("signUpForm()");
		//src/main/resources의 templates 에 singUpForm.html과 이름이 같아야 연결된다.
		//매핑되는 URL 주소는 GetMapping 어노테이션에 있다.
		return "signUpForm"; //응답에 사용하는 html 파일 이름 반환
	}
	
	//로그인폼 매핑
	@GetMapping("/member/signin") 
	public String signInForm() {
		System.out.println("signInForm()");
		return "signInForm"; //응답에 사용하는 html 파일 이름 반환
	}
	
	//회원가입한 데이터가 signUpResult 페이지로 전달되는 메소드
	//가입한 자료를 매개변수로 넘겨줘야하므로 @RequestParam 사용한다.
	// @RequestParam()으로 받는 매개변수가 많을 때는 DTO를 따로 만들어서 클래스를 데이터타입으로 전송한다.0
	@PostMapping("/member/signUp_confirm")
	public ModelAndView signupconfirm(MemberDTO mdto) {
		
		//MemberService 비즈니스 로직을 담당하는 클래스를
		//new 라는 키워드를 이용하여 객체생성
		//MemberService memberservice = new MemberService();
		memberService.signUpConfirm(mdto);
		
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
			ModelAndView modelview = new ModelAndView();
			modelview.addObject("now",sd.format(now));
			modelview.addObject("new_id", mdto.getId());
			modelview.addObject("new_pw", mdto.getPw());
			modelview.addObject("new_email", mdto.getEmail());
			modelview.setViewName("signUpResult");

		return modelview;
	}
	//로그인 결과화면
	//ModelAndView 클래스는 model과 View를 하나로 합쳐서 클라이언트에 전달한다.
	@PostMapping("/member/signIn_confirm")
	public ModelAndView signinconfirm(MemberDTO mdto) {
		
		//MemberService 비즈니스 로직을 담당하는 클래스를
		//new 라는 키워드를 이용하여 객체생성
		//MemberService memberservice = new MemberService();
		memberService.signInConfirm(mdto);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("log_id", mdto.getId());
		mv.addObject("log_pw", mdto.getPw());
		mv.setViewName("signInResult");
		return mv;
		
	}
	
}
//@Controller
//public class MemberController {
//	// 아래 작성한 메소드들은 핸들러 메소드들이다.
//	//회원 가입양식
//	//http://localhost:8090/member/signup 의 경로를 매핑(=연결)
//	@GetMapping("/member/signup") 
//	public String signUpForm() {
//		//아래의 프린트문은 log역할
//		System.out.println("signUpForm()");
//		//src/main/resources의 templates 에 singUpForm.html과 이름이 같아야 연결된다.
//		//매핑되는 URL 주소는 GetMapping 어노테이션에 있다.
//		return "signUpForm"; //응답에 사용하는 html 파일 이름 반환
//	}
//	
//	//로그인폼 매핑
//	@GetMapping("/member/signin") 
//	public String signInForm() {
//		System.out.println("signInForm()");
//		return "signInForm"; //응답에 사용하는 html 파일 이름 반환
//	}
//	
//	//회원가입한 데이터가 signUpResult 페이지로 전달되는 메소드
//	//가입한 자료를 매개변수로 넘겨줘야하므로 @RequestParam 사용한다.
//	@PostMapping("/member/signUp_confirm")
//	public String signupconfirm(
//			//현재 가입한 시간을 출력하는 로직을 작성
//			//시간 출력 형식 : 2026-01-21 11:20:10
//			//날짜 형식 클래스 : SimpleDateFormat();
//			@RequestParam("id") String id,
//			@RequestParam("pw") String pw,
//			@RequestParam("email") String email,
//			Model model
//			) {
//		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date now = new Date();
//		
//		//model로 담기
//		model.addAttribute("now",sd.format(now));
//		model.addAttribute("new_id",id);
//		model.addAttribute("new_pw",pw);
//		model.addAttribute("new_email",email);
//		return "signUpResult";
//	}
//	//로그인 결과화면
//	@PostMapping("/member/signIn_confirm")
//	public String signinconfirm(
//			@RequestParam("id") String id,
//			@RequestParam("pw") String pw,
//			Model model
//			) {
//		model.addAttribute("log_id",id);
//		model.addAttribute("log_pw",pw);
//		//리턴으로 주소
//		return "signInResult";
//	}
//	
//	
//}

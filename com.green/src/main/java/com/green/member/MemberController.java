package com.green.member;

import java.util.List;

import com.green.HomeController;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberController {
	
	//MemberService 클래스를 DI로 의존객체화 해야한다.
	@Autowired
	MemberService memberservice;

	// 회원가입 양식 폼
	@GetMapping("/member/signup")
	public String signup() {
		System.out.println("MemberController signup()메소드 확인");
		String nextPage = "member/signup_form";
		return nextPage;
	}
	
	// 회원가입 확인
	@PostMapping("/member/signup_confirm")
	public String signupConfirm(MemberDTO mdto, Model model) {
		System.out.println("MemberController signupConfirm()메소드 확인");
		String nextPage = "member/signup_result";
		//회원가입이 제대로 되었는지, 혹은 회원가입이 실패했는지 예외처리
		int result = memberservice.signupConfirm(mdto);
		model.addAttribute("result",result);
		//회원가입이 성공하였을경우 => 회원목록인 새로운 주소로 이동
		if(result == memberservice.user_id_success) {
			return nextPage;
		}else {
			//회원가입이 실패한경우
			return nextPage;
		}
	}
	
	//회원 전체 목록화면 호출
	@GetMapping("/member/list")
	public String memberList(Model model) {
		System.out.println("MemberContoller memberList 회원전체목록화면 출력");
		// MemberService의 allListMember()
		List<MemberDTO> memberlist = memberservice.allListMember();
		model.addAttribute("list",memberlist);
		String nextPage = "member/memberList";
		return nextPage;
		
	}
	
	//------------------------2026년01월27일 추가 Controller 추가 로직-----------------------------
	//한 개인의 정보를 상세보기하는 핸들러
	@GetMapping("/member/memberInfo")
	public String memberInfo(Model model, @RequestParam("id") String id) {
		System.out.println("memberContoller memberInfo()메소드 호출");
//		MemberDTO onememberinfo = memberservice.oneSelect(mdto.getId());
		MemberDTO onememberinfo = memberservice.oneSelect(id);
		
		model.addAttribute("onelist", onememberinfo);
		String nextPage = "member/memberInfo";
		return nextPage;	
	}
	
	//개인정보 수정하는 핸들러 /리다이렉트로보여줘야하나 html로 인풋만들어서 수정페이지만들라는건가
	//수정이 됏냐 안됏냐 말고도 수정처리? 핸들러 까지 2개가 필요하다고하심 아무것도모르겠음.
	@GetMapping("/member/modify")
	public String modifyform(MemberDTO mdto, Model model) {
		System.out.println("memberContoller modifyform()메소드 호출");
		
		// 개인수정 화면 그릴때 필요한 정보 : 한사람의 데이터
		// oneSelect(String id) : memberservice의 메소드
		MemberDTO oneModify = memberservice.oneSelect(mdto.getId());
		model.addAttribute("member",oneModify);
		String nextPage = "member/member_modify";
		return nextPage;
		
	}
	
	//개인정보를 수정처리하는 핸들러
	//비밀번호가 일치하는지의 비교에 관련된 핸들러
	//redirect 사용 해볼것
	//modifyMember()메소드 이용해볼것
	
	@PostMapping("/member/modify")
	public String modifySubmit(MemberDTO mdto, RedirectAttributes re) {
		System.out.println("memberContoller modifySubmit()메소드 호출");
		boolean result = memberservice.modifyMember(mdto);
		//지금 현재 result 결과값은 true or false 
		if(result == true) { // update 성공
			// RedirectAttributes 단 한번만 데이터를 넘길 수 있다.
			re.addFlashAttribute("msg","회원정보가 수정되었습니다.");
			//수정이 완료되면 list의 url로 이동
			return "redirect:/member/list";
		}else { // 서비스에서 온 modifyMember 반환값이 false일 때 , // update 실패
			re.addFlashAttribute("msg","비밀번호가 일치하지 않습니다.");
			//비밀번호가 틀리면 현재화면은 modify에 그대로 남아야한다.
			return "redirect:/member/modify?id="+mdto.getId();
		}
	}
	
	//개인 한사람의 정보를 삭제하는 핸들러
	@GetMapping("/member/delete")
	public String deleteMember(
			@RequestParam("id") String id,
			RedirectAttributes re
			) {
		System.out.println("memberContoller deleteMember()메소드 호출");
		boolean result = memberservice.oneDelete(id);
		//result 삭제가 된 경우 true, 안된경우 false
		if(result == true) {
			re.addFlashAttribute("msg", "회원이 삭제되었습니다.");
			//삭제 된 경우 list주소로 넘겨주기
			return "redirect:/member/list";
		}else { //삭제 안된경우 주소에 머물러야함
			re.addFlashAttribute("msg", "회원 삭제를 실패했습니다.");
			return "redirect:/member/memberInfo?id="+id;
		}
	}
	//로그인시 로그인 홈화면으로 이동하는 컨트롤러
	@GetMapping("/member/login")
	public String loginForm() {
		System.out.println("memberContoller loginForm()메소드 호출");
		return "/member/login_form";
	}
	
	//로그인을 처리하기 위한 컨트롤러
	@PostMapping("/member/loginPro")
	public String loginPro(MemberDTO mdto, HttpSession session) {
		System.out.println("memberContoller loginPro()메소드 호출");

		MemberDTO loginMember = memberservice.loginConfirm(mdto);
		
		// model은 일회성이다
		// 그래서 화면이 이동되면 사라진다.
		// 로그인유지가 안됨.
		//Session() -> 스프링부트의 내장객체이다.
		//				  => 스프링부트에서 꺼내사용하기 편하다
		//HttpSession 클래스이름
		//Session() 이란?
		//			서버가 사용자 한명을 기억하기 위해 사용하는 저장공간
		// => 로그인 유지가 가능하다.
		
		//Session 기본 3가지 명령ㅇ
		// 1. 세션에 값 저장하기
		// 		session.setAttribute("이름",값) => 로그인 성공 시 사용
		// 2. 세션에 저장된 값 가져오기
		// 		session.getAttribute("이름") => 로그인 여부 확인
		// 3. 세션 전체 삭제
		//		session.invalidate(); => 로그아웃 시
		
		if(loginMember != null) {
			//로그인 성공
			//session 이용해서 loginMember 담기
			session.setAttribute("loginmember", loginMember);
			//홈으로 이동
			return "redirect:/";
		}else {
			return "redirect:/member/login";
		}
	}
	
	//로그아웃
	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
		System.out.println("memberContoller logout()메소드 호출");
		// 1. session에 담겨 있으므로 session.invalidate();
		session.invalidate();
		//로그아웃 시 홈으로 이동
		return "redirect:/";
	}
	
	
}

package com.green.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	
	@Autowired
	MemberService memberservice;
	
	//회원가입 폼
	@GetMapping("/member/signup")
	public String signup() {
		System.out.println("컨트롤러 signup()");
		String nextPage = "member/memberSignup";
		return nextPage;
	}
	
	//회원가입 처리
	@PostMapping("/member/signupresult")
	public String signupresult(MemberDTO mdto, Model model) {
		System.out.println("컨트롤러 signupresult()");
		int result = memberservice.addUser(mdto);
		model.addAttribute("result",result);
		if(result == 1) {
			return "member/memberSignupresult";
		}else {
			return "member/memberSignupresult";
		}
	}
	//전체 회원목록
	@GetMapping("/member/list")
	public String memberlist(Model model) {
		System.out.println("컨트롤러 memberlist()");
		List<MemberDTO> memberlist = memberservice.allUserList();
		model.addAttribute("list",memberlist);
		return "member/memberList";
	}
	//로그인폼 이동
	@GetMapping("/member/login")
	public String login() {
		System.out.println("컨트롤러 login()");
		return "/member/memberLogin";
	}
	//로그인처리  여기서 redirect로 로그인성공하면 홈 실패하면 다시 로그인폼
	@PostMapping("/member/loginCheck")
	public String logCheck(MemberDTO mdto, HttpSession session) {
		System.out.println("컨트롤러 logCheck()");
		MemberDTO whoLog = memberservice.logComplete(mdto);
		if(whoLog != null) {
			session.setAttribute("whoLog", whoLog);
			return "redirect:/";
		}else {
			return "redirect:/member/login";
		}
	}
	
	//로그아웃
	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
		System.out.println("컨트롤러 logout()");
		session.invalidate();
		return "redirect:/";
	}
	//회원정보 상세보기
	@GetMapping("member/memberMyPage")
	public String userInfo(Model model, @RequestParam("id")String id) {
		System.out.println("컨트롤러 userInfo()");
		MemberDTO oneUserInfo = memberservice.oneUser(id);
		model.addAttribute("oneUserInfo",oneUserInfo);
		return "member/memberMyPage";
	}
	
	//회원정보삭제
	@GetMapping("/member/delete")
	public String deleteUser(@RequestParam("id") String id,
							RedirectAttributes re) {
		System.out.println("컨트롤러 deleteUser()");
		boolean result = memberservice.oneDelete(id);
		if(result == true) {
			re.addFlashAttribute("msg","회원이 삭제되었습니다.");
			return "redirect:/member/list";
		}else {
			re.addFlashAttribute("msg","회원 삭제 실패.");
			return "redirect:/member/memberMyPage?id="+id;
		}
	}
	
	//회원정보수정
	@GetMapping("/member/modify")
	public String userMod(MemberDTO mdto, Model model) {
		System.out.println("컨트롤러 userMod()");
		MemberDTO oneMod=memberservice.oneUser(mdto.getId());
		model.addAttribute("member",oneMod);
		return "member/memberMod";
	}
	
	//회원정보 수정 처리
	@PostMapping("/member/modify")
	public String modComplete(MemberDTO mdto,RedirectAttributes re,HttpSession session) {
		System.out.println("컨트롤러 modComplete()");
		int result = memberservice.updateUser(mdto);
		if(result == 1) {
				re.addFlashAttribute("msg","회원 정보 수정 성공");
				return "redirect:/member/list";
			
		}else {
			re.addFlashAttribute("msg","회원 정보 수정 실패");
			return "redirect:/member/modify?id="+mdto.getId();
		}
	}
	
}

package com.green;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class QuizController {
	
	
	//정답 입력화면
	@GetMapping("/quiz")
	public String quizPage() {
		return "quiz";
	}
	
	//정답확인페이지
	@PostMapping("/check-quiz")
	public String checkPage(
			@RequestParam("pass") String pass,
			RedirectAttributes re
			) {
		if(pass.equals("1234")) {
			//메인뷰로이동
			//리다이렉트는 html파일명이 아니라 이동할 url주소를 적는다.
			return "redirect:/main";
		}else {
			//오답이면 다시 퀴즈페이지로 리턴
			re.addFlashAttribute("errorMsg","비밀번호가 틀렸습니다.");
			return "redirect:/quiz";
		}
	}
	
	//정답일때 메인페이지
	@GetMapping("/main")
	public String mainPage() {
		return "complete";
	}
}

package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BoardController {

	@Autowired
	BoardService boardservice;
	
	//회원 전체 목록화면 호출
	@GetMapping("/")
	public String showPostList(Model model) {
		System.out.println("Controller))===Board Controller showPostList  (게시글전체목록화면) 출력 확인===");
		// MemberService의 allListMember()
		List<BoardDTO> postList = boardservice.showAllPost();
		model.addAttribute("list",postList);
		String nextPage = "main";
		return nextPage;
	}
}

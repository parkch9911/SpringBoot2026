package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




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
	
	//게시글 하나 상세보기
	@GetMapping("/detail")
	public String postDetail(Model model, @RequestParam("id") int id) {
		System.out.println("Controller))===Board Controller postDetail (게시글 상세보기) 출력 확인===");
		System.out.println("memberContoller memberInfo()메소드 호출");
		BoardDTO oneboard = boardservice.selectPost(id);
		model.addAttribute("onePost", oneboard);
		String nextPage = "detail";
		return nextPage;	
	}

	//게시글 수정
	@GetMapping("/modify")
	public String modifyform(BoardDTO bdto, Model model) {
		System.out.println("Controller))===Board Controller modifyform (게시글 수정하기) 출력 확인===");
		BoardDTO oneModify = boardservice.selectPost(bdto.getId());
		model.addAttribute("post",oneModify);
		String nextPage = "modify";
		return nextPage;
		
	}
	//게시글 수정처리
	@PostMapping("/modify")
	public String modifyPost(BoardDTO bdto, RedirectAttributes re) {
		System.out.println("Controller))===Board Controller modifyPost (게시글 수정처리하기) 출력 확인===");
		boolean result = boardservice.changePost(bdto);
		if(result == true) {
			re.addFlashAttribute("msg","게시글이 수정되었습니다.");
			return "redirect:/";
		}else {
			re.addFlashAttribute("msg","게시글 수정을 실패했습니다.");
			return "redirect:/modify?id="+bdto.getId();
		}
	}
	
	//게시글 삭제하기
	@GetMapping("/delete")
	public String deletePost(
			@RequestParam("id") int id,
			RedirectAttributes re
			) {
		System.out.println("Controller))===Board Controller deletePost (게시글 삭제하기) 출력 확인===");
		boolean result = boardservice.removePost(id);
			re.addFlashAttribute("msg", "게시글이 삭제되었습니다.");
			return "redirect:/";
	}
	
		//게시글추가컨트롤러
		@GetMapping("/write")
		public String wirtePostCon() {
			System.out.println("Controller))===Board Controller wirtePostCon (게시글 추가하기) 출력 확인===");
			String nextPage = "write";
			return nextPage;
		}
		
		//게시글 처리 컨트롤러
		@PostMapping("/write/complete")
		public String postComplete(BoardDTO bdto, Model model) {
			System.out.println("Controller))===Board Controller postComplete (게시글 추가완료) 출력 확인===");
			boardservice.writePost(bdto);
				return "redirect:/";
		}
	
	
	
	
}

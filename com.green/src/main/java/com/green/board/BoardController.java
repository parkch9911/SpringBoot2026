package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {
	
	@Autowired
	BoardService boardservice;
	
	//
	@GetMapping("/board/write")
	public String boardWriteForm() {
		System.out.println("1)BoardController boardWriteForm()메소드 호출");
		String nextPage = "board/boardWrite_form";
		return nextPage;
		}
	
	// 2.포멧에 입력한 데이터를 DB에 영구저장하는 데이터 추가를 처리하는 컨트롤러
	@PostMapping("/board/writePro")
	public String boardWritePro(BoardDTO bdto) {
		System.out.println("1)BoardController boardWritePro()메소드 호출");
		
		//서비스의 addBoard()메소드를 호출하여 DB저장
		boardservice.addBoard(bdto);
		
		//저장 후에는 => 게시판 목록으로 페이지 이동(redirect)
		return "redirect:/board/list";
		
	}
	
	// 3. DB에서 전체 게시글 목록 select 로 검색하여 추출 -> 모델(model)객체 담는다.
	// 전체목록 화면 boardList.html 로 이동한다.
//	@GetMapping("/board/list")
//	public String boardList(Model model) {
//		System.out.println("1)BoardController boardList()메소드 호출");
//		List<BoardDTO> listboard = boardservice.allBoard();
//		model.addAttribute("list",listboard);
//		String nextPage = "board/boardList";
//		return nextPage;
//	}
	
	// 4. 하나의 게시글 상세정보 확인 핸들러
	// num이라는 글번호를 받아서 해당 게시글을 DB에서 조회하고 상세정보를 
	// boardInfo 에 전달하는 컨트롤러
	@GetMapping("/board/boardInfo")
	public String boardInfo(@RequestParam("num") int num,Model model) {
		System.out.println("1)BoardController boardInfo()메소드 호출");
		
		BoardDTO oneboardInfo = boardservice.OneBoard(num);
		model.addAttribute("oneboard",oneboardInfo);
		
		String nextPage="board/boardInfo";
		return nextPage;
	}
	
	// 5.게시글 수정 폼이동 컨트롤러
	@GetMapping("/board/update")
	public String boardUpdateForm(@RequestParam("num") int num,Model model) {
		System.out.println("1)BoardController boardUpdateForm()메소드 호출");
		//기존에 하나의 게시글을 불러오는 쿼리를 이용하여 수정한다. //OneBoard
		BoardDTO oneboardInfo = boardservice.OneBoard(num);
		model.addAttribute("oneboard",oneboardInfo);
		String nextPage = "board/boardUpdate_form";
		return nextPage;
		
	}
	
	// 6. 하나의 게시글 수정 처리하는 컨트롤러
	@PostMapping("/board/updatePro")
	public String boardUpdatePro(BoardDTO bdto, Model model) {
		System.out.println("1)BoardController boardUpdatePro()메소드 호출");
		
		boolean isSuccess = boardservice.modifyBoard(bdto);
		//수정 완료면 true 아니면 false
		if(isSuccess) {
			//수정 성공시 list로 이동
			return "redirect:/board/list";
		}else {
			//수정 실패시 현재 url에 머무르기
			return "redirect:/board/update?num="+bdto.getNum();
		}
	}
	
	// 7.하나의 게시글 삭제하는 컨트롤러
	// 현재 boardInfo.html 의 [삭제하기] 버튼 클릭하면 삭제됨
	// 삭제된 후 board/list로 이동
	// 삭제 실패 후는 boardInfo.html 에 머물러야한다.
	@GetMapping("/board/deletePro")
	public String boardDeletePro(
			@RequestParam("num") int num,
			@RequestParam("writerPw") String writerPw
			) {
		System.out.println("1)BoardController boardDeletePro()메소드 호출");
		//boardService 에서 removeBoard()메소드 삭제 : true, 실패:false
		boolean isSuccess = boardservice.removeBoard(num, writerPw);
		if(isSuccess) {
			return "redirect:/board/list";
		}else { //삭제 실패 시 페이지 머무르기
			return "redirect:/board/boardInfo?num="+num;
		}
	}

	// 검색을위한 board/list 커스텀하기
	@GetMapping("/board/list")
	public String boardList(Model model,
			@RequestParam(value="searchType",required=false) String searchType,
			@RequestParam(value="searchKeyword",required=false) String searchKeyword
			) {
		System.out.println("1)BoardController boardList()메소드 호출");
		
		List<BoardDTO> listboard;
		
		//검색 종료 후 => 검색내용이 list 나오기
		if(searchType != null && !searchKeyword.trim().isEmpty()) {
			//boardDAO에 검색메소드 getSearchBoard()호출한다.
			//service에서 searchBoard
			listboard = boardservice.searchBoard(searchType, searchKeyword);
		}else {
			listboard = boardservice.allBoard();
		}
		
		//검색하지 않고 전체보기 list 나오기
		model.addAttribute("list",listboard);
		String nextPage = "board/boardList";
		return nextPage;
	}
	
}

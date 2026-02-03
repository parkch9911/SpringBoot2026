package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.member.MemberDTO;

import jakarta.servlet.http.HttpSession;

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
	public String boardWritePro(BoardDTO bdto, HttpSession session) {
		System.out.println("1)BoardController boardWritePro()메소드 호출");
		
		// session.setAttribute("loginmember") 저장한 데이터를 꺼내와야 한다.
		// 세션에서 값 꺼내오는 메소드 session.getAttribute("loginmember")
		// Session 은 자바의 Object 최상위 객체이므로 다운캐스팅 하여야 한다.
		// 로그인 id => admin9867의 정보 한 형이 모두 MemberDTO 타입으로 
		// loginedMember 에 저장된다. 
		MemberDTO loginedMember = (MemberDTO)session.getAttribute("loginmember");
		
		//로그인 정보가 존재하는지 체크하는 코드가 필요하다.
		if(loginedMember != null) {
			//지금 현재 로그인 된 아이디는 loginedMember.getId()
			bdto.setId(loginedMember.getId());
			System.out.println("DB에 저장될 ID 확인 : "+loginedMember.getId());
		}else {
			System.out.println("로그인 실패");
			return "redirect:/member/login";
		}
		
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
//	}  밑에 리스트 또 있음
	
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
			@RequestParam(value="searchKeyword",required=false) String searchKeyword,
			//1. 페이지 번호 => 1부터 시작이므로 초기값 1로 정의한다.
			@RequestParam(value="page",defaultValue = "1") int page,
			//2. 페이지 사이즈 (한 화면에 보여지는 게시글의 수를 5로 초기화한다.
			@RequestParam(value="pageSize",defaultValue = "5") int pageSize
			) {
		System.out.println("1)BoardController boardList()메소드 호출");
		
		//3.전체 게시글 개수 totalCnt 메소드 가져오기
		int totalCnt = boardservice.getAllcount();
		
		//4.PageHandler 클래스 접근하기 위해 인스턴스화 한다.
		PageHandler ph = new PageHandler(totalCnt, page, pageSize);
		
		List<BoardDTO> listboard;
		
		//검색 종료 후 => 검색내용이 list 나오기
		if(searchType != null && !searchKeyword.trim().isEmpty()) {
			//boardDAO에 검색메소드 getSearchBoard()호출한다.
			//service에서 searchBoard
			listboard = boardservice.searchBoard(searchType, searchKeyword);
		}else {
			//검색하지 않고 List나오기
			// boardservice.allBoard() => 사용못하는 이유는
			// 페이징이 안된 모든 레코드가 출력되는 메소드이므로 사용금지
			listboard = boardservice.getPageList(ph.getStartRow(), pageSize);
		}
		//검색하지 않고 전체보기 list 나오기
		model.addAttribute("list",listboard);
		//PageHandler 클래스 모두 model 객체에 담아서 html로 보내야함
		//그래야 UI화면에 페이징 그릴 수 있다.
		model.addAttribute("ph",ph); // PageHandler 클래스를 인스턴스한 참조변수이다.
		String nextPage = "board/boardList";
		return nextPage;
	}
	//이 리스트에서 건드려야하겟지?
	
}

package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.board.mapper.BoardMapper;

@Service
public class BoardService {

	@Autowired
	//BoardDAO boardDao;
	BoardMapper boardMapper;
	
	//하나의 게시글이 추가되는 메소드를 BoardDAO 에 접근하여 사용해야함.
	public void addBoard(BoardDTO bdto) {
		System.out.println("3)BoardService addBoard()메소드 호출");
		boardMapper.insertBoard(bdto);
	}
	
	//게시글 전체 목록 출력
	public List<BoardDTO> allBoard() {
		System.out.println("3)BoardService allBoard()메소드 호출");
		return boardMapper.getAllBoard();
	}
	
	//두개로 나누라는게 어디서 나누라는거지? service에서 수정???//
	//하나의 게시물 출력하는 메소드
	public BoardDTO OneBoard(int num) {
		System.out.println("3)BoardService OneBoard()메소드 호출");
		//조회수 증가 메서드 추가하기
		boardMapper.upReadCount(num);
		//조회수 증가 + 하나 게시글 검색
		return boardMapper.getOneBoard(num);
	}
	
	//하나의 게시물 수정하는 메소드
	public boolean modifyBoard(BoardDTO bdto) {
		System.out.println("3)BoardService modifyBoard()메소드 호출");
		int result = boardMapper.updateBoard(bdto);
		if(result > 0) {
			System.out.println("게시물 수정 완료");
			return true;
		}else {
			System.out.println("게시물 수정 실패");
			return false;
		}
	}
	
	//게시글 하나 삭제하는 메소드
	public boolean removeBoard(int num, String writerPw) {
		System.out.println("3)BoardService removeBoard()메소드 호출");
		//DAO에서 받아오는 deleteBoard(는 삭제 =1 , 아니면 0
		int result = boardMapper.deleteBoard(num, writerPw);
		if(result > 0) {
			System.out.println("게시글 삭제 성공");
			return true;
		}else {
			System.out.println("게시글 삭제 실패");
			return false;
		}
	}
	
	//게시글 검색하는 메소드
	public List<BoardDTO> searchBoard(String searchType, String searchKeyword){
		System.out.println("3)BoardService SearchBoard()메소드 호출");
		System.out.println("3)searchType : "+searchType);
		System.out.println("3)searchKeyword : "+searchKeyword);
		return boardMapper.getSearchBoard(searchType, searchKeyword);
	}
	
	//전체 게시글 수 구하는 메소드
	public int getAllcount() {
		System.out.println("3)BoardService getAllcount()메소드 호출");
		return boardMapper.getAllCount();
	}
	
	//한 화면에서 뿌려지는 limit 구하는 메소드? startRow, pageSize 까지의 행 검색
	public List<BoardDTO> getPageList(int startRow, int pageSize){
		System.out.println("3)BoardService getPageList()메소드 호출");
		return boardMapper.getPageList(startRow, pageSize);
	}
	
}

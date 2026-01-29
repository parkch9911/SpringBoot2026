package com.green.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BoardService {

	@Autowired
	BoardDAO boarddao;
		
	//게시물 전체 출력=============================================================================
	public List<BoardDTO> showAllPost(){
		System.out.println("Service))===Board Service showAllPost(전체 게시물 출력 메소드) 출력 확인===");
		return boarddao.allPost();
	}
	
	//게시물 하나 thisPost 가져오는 메서드============================================================
	public BoardDTO selectPost(int id) {
		System.out.println("Service))===Board Service selectPost (게시물 하나의 id 가져오는 메소드) 출력 확인=== id : "+boarddao.thisPost(id));
			return boarddao.thisPost(id);
		}
	
	//게시물 하나 수정하는 메서드=====================================================================
	public boolean changePost(BoardDTO bdto) {
		int logCheck = boarddao.modPost(bdto);
		System.out.println("Service))===Board Service changePost(게시물 수정 메소드) 출력 확인=== boolean값 : "+logCheck);
		return boarddao.modPost(bdto) == 1;
	}
	
	//게시물 하나 삭제하는 메서드======================================================================
	public boolean removePost(int id) {
		System.out.println("Service))===Board Service removePost(게시물 삭제 메소드) 출력 확인===");
		return boarddao.delPost(id)==1;
	}
	
	//게시물 작성하는 메서드==========================================================================
	public int writePost(BoardDTO bdto) {
		System.out.println("Service))===Board Service writePost(게시물 작성 메소드) 출력 확인===");
		int completePost = boarddao.addPost(bdto);
		return completePost;
	}
	
	
}

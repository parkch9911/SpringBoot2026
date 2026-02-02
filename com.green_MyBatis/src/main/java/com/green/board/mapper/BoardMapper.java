package com.green.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.green.board.BoardDTO;

@Mapper
public interface BoardMapper {

	//하나의 게시글 작성하여 추가하는 쿼리문
	public void insertBoard(BoardDTO bdto);
	
	//전체 게시물 목록을 출력하는 쿼리문
	public List<BoardDTO> getAllBoard();
	
	//하나의 게시물 상세정보 보기
	public BoardDTO getOneBoard(int num);
	
	//readcount 누적하여 조회수 증가하는 메소드
	public int upReadCount(int num);
	
	//하나의 게시글을 수정하는 메소드
	public int updateBoard(BoardDTO bdto);
	
	// 게시글 작성 시 비밀번호 입력하였기때문에 삭제시에도 비밀번호와 번호가 일치하는지 체크
	//@Param("변수") 데이터타입 필드명
	public int deleteBoard(@Param("num")int num,@Param("writePw")String writerPw);
	
	//내용또는 제목으로 게시글 검색하는 메소드
	//검색메소드 반드시, searchType, searchKeyword 매개변수 필요
	//매개변수가 두개 이상이면 @Param으로 받는다.
	public List<BoardDTO> getSearchBoard(@Param("searchType") String searchType,
										@Param("searchKeyword")String searchKeyword);
	
	
}

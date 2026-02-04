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
	
	//전체 게시글 수 구하는 메소드
	public int getAllCount();
	
	//전체 게시글의 시작 (startRow),몇개의 행(pageSize) 만큼 보는 메소드
	public List<BoardDTO> getPageList(@Param("startRow") int startRow,
									  @Param("pageSize") int pageSize);
	
	//검색 페이징 필요한 메소드 생성하기 =============================
	// searchType, searchKeyword 에 해당하는 검색된 개수를 반환하는 메소드
	public int getSearchCount(@Param("searchType")String searchType,
							  @Param("searchKeyword")String searchKeyword);
	
	// searchType, searchKeyword, startRow, pageSize => 
	// limit startRow 부터, pageSize개 만큼 한 화면에 보여질 행의 개수
	public List<BoardDTO> getSearchPageList(@Param("searchType")String searchType,
										    @Param("searchKeyword")String searchKeyword,
										    @Param("startRow") int startRow,
										    @Param("pageSize") int pageSize);
	
	// 로그인이 된 상태에서 나만의 게시글을 mypage.html에 출력
	public List<BoardDTO> getMyBoardList(@Param("loginId")String loginId,
									 @Param("startRow") int startRow,
								     @Param("pageSize") int pageSize);
	
	//로그인된 나만의 게시물의 개수
	public int getMyBoardCount(String loginId);
}

package com.green.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.board.BoardDTO;

@Mapper
public interface BoardMapper {
	//전체 게시물============
	public List<BoardDTO> allPost();
	
	//게시물 작성 (추가) 하는 쿼리====
	public int addPost(BoardDTO bdto);
	
	//게시물 하나 검색하는거
	public BoardDTO thisPost(int id);
	
	//게시물 수정하는 쿼리 
	public int modPost(BoardDTO bdto);
	
	//게시물 삭제하는 쿼리 -- 
	public int delPost(int id);
	
}

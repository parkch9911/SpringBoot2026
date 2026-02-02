package com.green.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.member.MemberDTO;

@Mapper
public interface MemberMapper {
	
	//전체 회원 목록 뽑아낼거
	public List<MemberDTO> allUser();
		
	//회원 추가 하는거
	public int addUser(MemberDTO mdto);
	
	//한사람만 출력하는거
	public MemberDTO oneUser(String id);
	
	//회원 정보 수정하기
	public int updateUser(MemberDTO mdto);
	
	//회원 정보 삭제하기
	public int deleteUser(String id);
	
}

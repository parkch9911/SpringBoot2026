package com.green.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.member.mapper.MemberMapper;

@Service
public class MemberService {

	@Autowired
	//MemberDAO memberdao;
	MemberMapper memberMapper;
	
	//회원 전체 목록 출력
	public List<MemberDTO> allUserList(){
		return memberMapper.allUser();
	}
	
	//회원 추가 하는거
	public int addUser(MemberDTO mdto) {
		return 1;
	}
	
	//한사람만 출력하는거
	public MemberDTO oneUser(String id) {
		return memberMapper.oneUser(id);
	}

	//회원 정보 수정하기
	public int updateUser(MemberDTO mdto) {
		return 1;
	}
	
	//개인 한사람의 회원정보를 삭제하는 메소드
	public boolean oneDelete(String id) {
		return memberMapper.deleteUser(id)==1;
	}
	
}

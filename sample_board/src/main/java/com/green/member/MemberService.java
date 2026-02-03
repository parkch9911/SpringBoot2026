package com.green.member;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.member.mapper.MemberMapper;

@Service
public class MemberService {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	//MemberDAO memberdao;
	MemberMapper memberMapper;
	
	//회원 전체 목록 출력
	public List<MemberDTO> allUserList(){
		System.out.println("서비스 전체목록출력 allUserList()");
		return memberMapper.allUser();
	}
	
	//회원 추가 하는거
	public int addUser(MemberDTO mdto) {
		System.out.println("서비스 회원 추가 addUser()");
		String encodePw = passwordEncoder.encode(mdto.getPw());
		mdto.setPw(encodePw);
		int result = memberMapper.addUser(mdto);
		if(result > 0) {
			return 1;
		}else {
			return 0;
		}
		
	}
	
	//한사람만 출력하는거
	public MemberDTO oneUser(String id) {
		System.out.println("서비스 한사람출력 oneUser()");
		return memberMapper.oneUser(id);
	}

	//회원 정보 수정하기
	public int updateUser(MemberDTO mdto) {
		System.out.println("서비스 회원수정 updateUser()");
		return 1;
	}
	
	//개인 한사람의 회원정보를 삭제하는 메소드
	public boolean oneDelete(String id) {
		System.out.println("서비스 회원삭제 oneDelete()");
		return memberMapper.deleteUser(id)==1;
	}
	
	//복호화하는거 따로 새로안만들고 oneselect로 
	public MemberDTO logComplete(MemberDTO mdto) {
		System.out.println("서비스 복호화 logComplete()");
		MemberDTO users = memberMapper.oneUser(mdto.getId());
		//db에서 가져온거 널포인트익셉션 db에잇는 user가 있으면서 비밀번호가 존재하면
		if(users != null && users.getPw() != null) {
			//복호화해서 db id에 해당하는 비번과 비교하기
			if(passwordEncoder.matches(mdto.getPw(), users.getPw())) {
				return users;
			}
		}
		return null;
		
	}
	
}

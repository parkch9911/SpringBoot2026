package com.green;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

//DAO 쿼리문 집합소(?) => 데이터를 직접 처리하는 객체
@Repository //데이터 저장소다. 라는 DAO객체라고 알려준다.
public class MemberDAO {
	//원래는 DB 커넥션이 존재해야하지만, 현재 DB가 존재하지 않으므로
	// HashMap<>을 이용해 DB처럼 사용하고자한다.
	// HashMap의 keyset은 id로 지정하기로한다.
	private Map<String, MemberDTO> memberDB = new HashMap<String, MemberDTO>();
	
	
	//Insert메소드생성
	public void insertMember(MemberDTO mdto) {
		System.out.println("회원가입을 추가하는 메소드입니다.");
		//쿼리문 
		memberDB.put(mdto.getId(),mdto);
		printMember();
	}
	
	//회원정보 출력 메서드
	public void printMember() {
		for(String key : memberDB.keySet()) {
			MemberDTO mdto = memberDB.get(key);
			System.out.println("id : "+mdto.getId());
			System.out.println("pw : "+mdto.getPw());
		}
	}
	
	//Select메소드생성
	public MemberDTO selectMember(MemberDTO mdto) {
		System.out.println("로그인 정보를 확인하는 메소드입니다.");
		// 아이디와 비밀번호를 비교해서 같으면 로그인성공 / 아니면 로그인실패
		// loginMember = {"kjb1045","111","kkk@naver.com"};
		MemberDTO loginMember =  memberDB.get(mdto.getId());
		return loginMember;
	}
}

package com.green.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// controller -> service : DAO 메소드 찾아있어 
// DAO야 메소드 있어 -> DB에서 찾아옴
// DB -> id, pw값들고  -> DAO로 보냄 -> service의 메소드로 보냄
// -> controller에게 id, pw 찾아서 보냄
@Service
public class MemberService {

	// id중복체크, 성공, 실패 상수변수 정의 
	// 회원가입의 중복을 확인하는 상수
	public final static int user_id_alreday_exit = 0;
	// 회원가입의 성공여부를 확인하는 상수
	public final static int user_id_success = 1;
	// 회원가입의 실패를 확인하는 상수
	public final static int user_id_fail = -1;
	
	//MemberDAO도 DI를 정의한다.
	@Autowired
	MemberDAO memberdao;

	//회원 전체 목록 출력하는 메소드
	public List<MemberDTO> allListMember(){
		return memberdao.allSelectMember();
	}
	
	//회원가입이 제대로 되었는지, 혹은 회원가입이 실패했는지 예외처리
	public int signupConfirm(MemberDTO mdto) {
		System.out.println("MemberService signupConfirm()메소드 확인");
		
		//회원가입 중복체크
		//id 없음 => flase
		boolean isMember = memberdao.isMember(mdto.getId());
		//회원가입 중복체크 통과했다면
		if(isMember == false) {
			// 중복된 아이디가 존재하지 않을 때 DB에 회원의 정보가 추가된다.
			int result = memberdao.insertMember(mdto);
			if(result > 0 ) {
				return user_id_success; // result = 1
			}else {
				return user_id_fail; // result = -1
			}
		}else {
			// 중복된 아이디가 존재할 때
			return user_id_alreday_exit; // result = 0;
		}
	}
	
	

}

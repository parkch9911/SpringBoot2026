package com.green;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// MemberService 클래스는 비즈니스 로직을 작성하는 클래스이다.
// @Service의 의미이다.
@Service
public class MemberService {

	// MemberDAO 클래스를 MemberService 클래스에서 사용하는 방법
	// DI를 이용해 외부로부터 객체를 주입하여 사용한다.
	// DI를 의미하는 @Autowired를 사용한다.
	@Autowired
	MemberDAO memberDAO;
	
	public void signUpConfirm(MemberDTO mdto) {
		System.out.println("회원가입 출력화면입니다");
		memberDAO.insertMember(mdto);
	}

	public void signInConfirm(MemberDTO mdto) {
		System.out.println("로그인 출력화면입니다");
		MemberDTO loginMember = memberDAO.selectMember(mdto);
				if(loginMember != null && mdto.getId().equals(loginMember.getId())
						&& mdto.getPw().equals(loginMember.getPw())
						) {
					System.out.println("id : "+loginMember.getId());
					System.out.println("pw : "+loginMember.getPw());
					System.out.println("로그인 성공");
				}else {
					System.out.println("로그인 실패");
				}
	}
	
	//if문을 이용해서 idpw가 DB에 존재하면 로그인성공 , 아니면 로그인실패 출력하기
			//DB가 비어있지 않고 존재하면
			//Cannot invoke "com.green.MemberDTO.getId()" because "loginMember" is null
			//null point exception //입력하지않은채로 실행
	//암묵적으로 ui에 입력받는값을 equals() 안에 넣자
}

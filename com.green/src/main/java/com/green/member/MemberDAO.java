package com.green.member;

import com.green.HomeController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository // 데이터 저장소
public class MemberDAO {

	//MySQL Driver 설치 및 JDBC 환경 설정 완료
	//외부에서 DataSource 를 DI로 삽입한다.
	
	@Autowired
	private DataSource dataSource;	
	
	
	//회원 전체 목록 검색 쿼리
	public List<MemberDTO> allSelectMember(){
		System.out.println("MemberDAO allSelectMember()메소드 확인");
		//전체목록검색 sql
		String sql = "SELECT * FROM user_member";
		//List<E> 인터페이스이므로 => 구현할 수 없다.
		//고로 ArrayList<>를 이용해야한다.
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		
		try(
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
				//select 구문은 executeQuery()를 실행한 결과를
			ResultSet rs = pstmt.executeQuery();
				  ){
			
			// rs의 결과 값
			// no id pw mail phone reg_date  mod_date
			// 1  1  1   1     1     2026~     2026~
			//rs.next() 다음행의 값이 존재하면 true 아니면 false를 반환한다.
			//while문은 rs.next() 가 false가 되는 순간 종료
			//while문의 rs.next()는 먼저 한 행을 루프하고 , 그 다음행...
			while(rs.next()) {
				MemberDTO mdto = new MemberDTO();
				//mdto 가방을 rs의 결과값을 저장하는 용도
				mdto.setNo(rs.getInt("no"));
				mdto.setId(rs.getString("id"));
				mdto.setPw(rs.getString("pw"));
				mdto.setMail(rs.getString("mail"));
				mdto.setPhone(rs.getString("phone"));
				mdto.setReg_date(rs.getString("reg_date"));
				mdto.setMod_date(rs.getString("mod_date"));
				
				//ArrayList에 추가하기
				list.add(mdto);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
		}
	
   // 쿼리문 사용할 공간
   public int insertMember(MemberDTO mdto) {
	  System.out.println("MemberDAO insertMember()메소드 확인");
	  
	  //실무에서 쿼리문 작성 시 대문자로 작성한다.
	  // NO, REG_DATE , MOD_DATE 는 default값이 존재하기때문에 삽입하지 않아도 된다.
	  // 추가할 필드명이 정해져 있을 경우 반드시 (필드명1, 필드명2, ... ) 필드명을 명시하다.
	  //INSERT INTO user_member(id, pw, mail, phone) VALUES(?,?,?,?);
	  //모든 sql문은 큰따옴표 안에 엮는다.
	  String sql = "INSERT INTO user_member(id,pw,mail,phone) VALUES(?,?,?,?)";
	  int result = 0;
	  
	  //DB는 네트워크를 통해 자료를 가져오므로 try ~ catch() 구문 이용한다.
	  try(
			  //Connection 클래스를 이용해 dataSource를 getConnection()해야한다.
			  //사용하고 나면 반드시 반납을 해야한다. close()를 해야함.
			  //그러나 try() 괄호 안에 Connection 을 넣으면 자동으로 반납(close())된다.
			  Connection conn = dataSource.getConnection();
			  PreparedStatement pstmt = conn.prepareStatement(sql);
		
			  ){
		  
		  //?,?,?,? 값을 대치시켜줘야한다.
		  // input = 입력 => mdto 가방에 담긴 상태
		  // mdto 라는 가방에서 필요한 자원을 getId() 꺼내온다.
		  pstmt.setString(1, mdto.getId());
		  pstmt.setString(2, mdto.getPw());
		  pstmt.setString(3, mdto.getMail());
		  pstmt.setString(4, mdto.getPhone());
		  //										실행하다
		  //insert , delete , update 구문은 실행명령 : executeUpdate()이다. 
		  result = pstmt.executeUpdate();
		  //executeUpdate()메소드의 의미는 insert, delete, update문을 실행하고나면
		  //실행결과를 int 데이터 타입의 행의 개수로 반환한다는 의미
		  //insert 1건 성공 => 반환값 : 1 
		  //insert 0건 중복체크 => 반환값 : 0 
		  //update 3건 수정 => 반환값 : 3 
		  //delete 5건 삭제 => 반환값 : 5 
		  
		  System.out.println("MemberDAO insertMember result값 : "+result);
	  }catch(Exception e) {
		  e.printStackTrace();
	  }
	  return result;	  
   }
   
   //
   //
   public boolean isMember(String id) {
	   System.out.println("MemberDAO isMember()메소드 확인");
	   return false;
   }
   
   //---------------------2026년 1월 27일 추가쿼리 작성부분------------------------
   //개인 한 사람의 정보를 검색하는 메소드
   public MemberDTO oneSelectMember(String id) {
	   //log는 반드시 찍는다.
	   System.out.println("MemberDAO oneSelectMember()메소드 호출");
	   //반환받을 MemberDTO 객체를 생성한다.
	   MemberDTO mdto = new MemberDTO();
	   //SQL 구문 작성 
	   String sql = "SELECT * FROM user_member WHERE id=?";
	   //예외처리하는 try(자동 close를 위해 Connection 설정) ~ catch()
	   try(
			  Connection conn = dataSource.getConnection();
			  PreparedStatement pstmt = conn.prepareStatement(sql);
			   ){
		   		//실행문 작성은 여기
		   		// ? 대응을 먼저 작성한다.
		   	pstmt.setString(1, id); //여기서 1 은 sql문의 첫번째 물음표 대응 순서
		   	// select 문은 반드시 ResultSet객체에 담는다.
		   	ResultSet rs = pstmt.executeQuery();
		   	//mdto.setid(~~) 담는다. 
		   	//rs.next() 없이 값을 꺼내오면 항상 빈 DTO임을 주의하자 
		   	//배열이 아닌 한사람의 정보를 꺼내올때도 if 로 물어봐주어야함. 
		   	if(rs.next()){
			   	mdto.setNo(rs.getInt("no"));
				mdto.setId(rs.getString("id"));
				mdto.setPw(rs.getString("pw"));
				mdto.setMail(rs.getString("mail"));
				mdto.setPhone(rs.getString("phone"));
				mdto.setReg_date(rs.getString("reg_date"));
				mdto.setMod_date(rs.getString("mod_date"));
		   	}
			
	   }catch(Exception e) {
		   e.printStackTrace();
	   }   
	return mdto; 
   }
   
   //개인 한사람의 정보를 수정하는 쿼리
   public int updateMember(MemberDTO mdto) {
	   System.out.println("MemberDAO updateMember()메소드 호출");
	   int result = 0;
	   String sql = "UPDATE user_member SET mail=?,phone=? WHERE id=?";
	   try(
			   Connection conn = dataSource.getConnection();
			   PreparedStatement pstmt = conn.prepareStatement(sql);
			   ){
		   	//물음표 3개 대응해야한다.
		   pstmt.setString(1, mdto.getMail());
		   pstmt.setString(2, mdto.getPhone());
		   pstmt.setString(3, mdto.getId());
		   result = pstmt.executeUpdate();
		   System.out.println("UPDATE result = "+result);
		   
	   }catch(Exception e) {
		   e.getStackTrace();
	   }
	   return result;
   }
   
   //개인 한사람의 패스워드만 리턴하는 쿼리
   public String getPass(String id) {
	   System.out.println("MemberDAO getPass()메소드 호출");
	   String pass = "";
	   String sql = "SELECT pw FROM user_member WHERE id=?";
	   try(
			   Connection conn = dataSource.getConnection();
			   PreparedStatement pstmt = conn.prepareStatement(sql);
			   
			   ){
		   pstmt.setString(1, id);
		   ResultSet rs = pstmt.executeQuery(); //ResultSet 은 SELECT 문 일때만 사용한다.
		   if(rs.next()) {
			   pass = rs.getString(1); //패스워드값에 저장된 MappingIndex
		   }
		   System.out.println("getPass pw = "+pass);
	   }catch(Exception e) {
		   e.getStackTrace();
	   }
	   return pass;
   }
   
   // 단, 정보를 수정할 때 패스워드가 일치하는지 확인할것
   
   //한사람 개인의 정보를 삭제하는 메소드 작성
   public int deleteMember(String id) {
	   int result = 0;
	   String sql = "DELETE FROM user_member WHERE id=?";
	   try(
			   Connection conn = dataSource.getConnection();
			   PreparedStatement pstmt = conn.prepareStatement(sql);
			   ){
		   pstmt.setString(1, id);
		   //쿼리문 실행할때 executeUpdate() 는 DELETE , INSERT , UPDATE 문에 사용한다.
		   //executeQuery() 는 SELECT 문에 사용한다.
		   result = pstmt.executeUpdate();

	   }catch(Exception e) {
		   e.getStackTrace();
	   }
	   return result;
   }
 
   
}

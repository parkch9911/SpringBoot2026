package com.green.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	
	// application.properties 설정된 환경의 MySQL과 연결한다라는 의미.
	@Autowired
	private DataSource datasource;
	
	// 쿼리문 작성시 KetPoint 
	// 1. 한사람, 하나에 관련된 자료를 INSERT 하거나 SELECT 할 때는
	//    => DTO 객체에 담아 사용한다. 고로, 데이터타입은 DTO가 된다.
	// 2. 전체 목록, 전체 회원... 복수에 해당하는 자료는
	//    => List<E> list = new ArrayList<E> 에 담는다.
	// 3. 필드명하나 SELECT 할 때 
	//    => 데이터타입 : Stirng, int, boolean 등...
	// 4. 메소드 작성 시 void는 반환값(return)이 필요없다.
	// 5. 메소드 작성 시 데이터타입이 존재하면 반환값(return)이 필요하다.
	// 6. try(){}catch(){} 사용
	
	//하나의 게시글 작성하여 추가하는 쿼리문
	public void insertBoard(BoardDTO bdto) {
		System.out.println("2)BoardDAO insertBoard()메소드 호출");
		//추가하는 쿼리문
		String sql = "INSERT INTO otboard(writer, subject, writerPw, content) VALUES(?,?,?,?)";
		
		try(
				//datasource 주입한 데이터베이스원본의 자료들을 연결하세요
				Connection conn = datasource.getConnection();
				// conn = (url, username, userPassword)   
				// conn = (localhost:3306, "green", "12345678")   				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			//실행문 작성하는곳
			// 제일먼저 ? 대응
			pstmt.setString(1, bdto.getWriter());
			pstmt.setString(2, bdto.getSubject());
			pstmt.setString(3, bdto.getWriterPw());
			pstmt.setString(4, bdto.getContent());
			// SELECT문은 executeQuery()
			// UPDATE,INSERT,DELETE는 executeUpdate()
			// SELECT 문은 반드시 Resultset에 담아 출력한다.
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//전체 게시물 목록을 출력하는 쿼리문
	public List<BoardDTO> getAllBoard(){
		System.out.println("2)BoardDAO getAllBoard()메소드 호출");
		//List<>인스턴스화 한다.
		List<BoardDTO> boardlist = new ArrayList<BoardDTO>();
		//sql문 작성 
		String sql = "SELECT * FROM otboard ORDER BY num DESC";
		try(
				Connection conn = datasource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			//실행문
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO bdto = new BoardDTO();
				bdto.setNum(rs.getInt("num"));
				bdto.setWriter(rs.getString("writer"));
				bdto.setSubject(rs.getString("subject"));
				bdto.setWriterPw(rs.getString("writerPw"));
				bdto.setReg_date(rs.getString("reg_date"));
				bdto.setReadcount(rs.getInt("readcount"));
				bdto.setContent(rs.getString("content"));
				boardlist.add(bdto);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return boardlist;
	}
	
	//하나의 게시물 상세정보 보기
	//readcount 누적하여 조회수 증가하는 메소드
	public BoardDTO getOneBoard(int num) {
		System.out.println("2)BoardDAO getOneBoard()메소드 호출");
		//BoardDTO 인스턴스화 한다.
		BoardDTO bdto = new BoardDTO();
		String sql = "UPDATE otboard SET readcount=readcount+1 WHERE num=?";
		String sql2 = "SELECT * FROM otboard WHERE num=?";
		//readcount 클릭할때마다 ++;
		
		//조회수증가 trycatch 
		//하나의 게시글 정보 가져오는 sql2 trycatch
		try(Connection conn = datasource.getConnection();)
		{	
			//조회수 trycatch
			try(PreparedStatement pstmt = conn.prepareStatement(sql)){
				//물음표대응
				pstmt.setInt(1, num);
				pstmt.executeUpdate();
			}
			//하나의 게시글 정보 가져오기 trycatch
			try(PreparedStatement pstmt = conn.prepareStatement(sql2)){
				pstmt.setInt(1, num);
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) {
					bdto.setNum(rs.getInt("num"));
					bdto.setWriter(rs.getString("writer"));
					bdto.setSubject(rs.getString("subject"));
					bdto.setWriterPw(rs.getString("writerPw"));
					bdto.setReg_date(rs.getString("reg_date"));
					bdto.setReadcount(rs.getInt("readcount"));
					bdto.setContent(rs.getString("content"));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return bdto;
	}
	
	
	//하나의 게시글을 수정하는 메소드
	public int updateBoard(BoardDTO bdto) {
		System.out.println("2)BoardDAO updateBoard()메소드 호출");
		int result = 0; //수정되면 1 아니면 0
		// 수정할 때 반드시 글번호와 비밀번호가 일치해야만 수정하는 쿼리
		String sql = "UPDATE otboard SET subject=?, content=? WHERE num=? AND writerPw=?";
		try(
				Connection conn = datasource.getConnection();				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			pstmt.setString(1, bdto.getSubject());
			pstmt.setString(2, bdto.getContent());
			pstmt.setInt(3, bdto.getNum());
			pstmt.setString(4, bdto.getWriterPw());
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//2026-01-29일 시작부분=====================================================================
	// 게시글 작성 시 비밀번호 입력하였기때문에 삭제시에도 비밀번호와 번호가 일치하는지 체크
	public int deleteBoard(int num, String writerPw) {
		System.out.println("2)BoardDAO deleteBoard()메소드 호출");
		int result =0;
		//삭제하는 SQL
		String sql = "DELETE FROM otboard WHERE num=? AND writerPw=?";
		
		try(
				Connection conn = datasource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			pstmt.setInt(1,num);
			pstmt.setString(2,writerPw);
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//내용또는 제목으로 게시글 검색하는 메소드
	//검색메소드 반드시, searchType, searchKeyword 매개변수 필요
	public List<BoardDTO> getSearchBoard(String searchType, String searchKeyword){
		System.out.println("2)BoardDAO getSearchBoard()메소드 호출");
		//리스트 인스턴스화
		List<BoardDTO> blist = new ArrayList<BoardDTO>();
		
		//sql
		String sql = "";
		if("subject".equals(searchType)) {
			// 입력한 문자를 포함하는 검색 명령어
			// SELECT 필드명 FROM 테이블명 WHERE 검색필드명 LIKE %키워드%;
			sql = "SELECT * FROM otboard WHERE subject LIKE ? ORDER BY num DESC";
		}else {
			//내용관련
			sql = "SELECT * FROM otboard WHERE content LIKE ? ORDER BY num DESC";
		}
		try(
				Connection conn = datasource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			//실행문
			pstmt.setString(1, "%"+searchKeyword+"%");
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO bdto = new BoardDTO();
				bdto.setNum(rs.getInt("num"));
				bdto.setWriter(rs.getString("writer"));
				bdto.setSubject(rs.getString("subject"));
				bdto.setWriterPw(rs.getString("writerPw"));
				bdto.setReg_date(rs.getString("reg_date"));
				bdto.setReadcount(rs.getInt("readcount"));
				bdto.setContent(rs.getString("content"));
				blist.add(bdto);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return blist;
	}
	
	
	
}

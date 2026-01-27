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
	
	@Autowired
	private DataSource dataSource;
	
	//전체 게시물=========================================================
	public List<BoardDTO> allPost(){
		System.out.println("Board DAO allPost (전체 게시글 메소드) 출력 확인");
		//전체글 목록 검색 sql
		String sql = "SELECT * FROM board";
		List<BoardDTO> postList = new ArrayList<BoardDTO>();
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				){
			while(rs.next()) {
				BoardDTO bdto = new BoardDTO();
				bdto.setId(rs.getInt("id"));
				bdto.setTitle(rs.getString("title"));
				bdto.setContent(rs.getString("content"));
				bdto.setWriter(rs.getString("writer"));
				bdto.setCreatedAt(rs.getString("createdAt"));
				postList.add(bdto);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return postList;
		
	}
	
	//게시물 작성 (추가) 하는 쿼리========================================
	public int addPost(BoardDTO bdto) {
		System.out.println("DAO))===Board DAO addPost (게시글 추가 메소드) 출력 확인===");
		String sql = "INSERT INTO board(title,content,writer) VALUES(?,?,?)";
		int result = 0;
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			pstmt.setString(1, bdto.getTitle()); // 1번째에 제목
			pstmt.setString(2, bdto.getContent());// 2번째에 내용
			pstmt.setString(3, bdto.getWriter());// 3번째에 작성자
			
			//아래 오른쪽 저거는 INSERT UPDATE DELETE 문에서만 사용하기
			result = pstmt.executeUpdate();
	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//게시물 하나 검색하는 쿼리 -- 상세페이지 접근하기위함 thisPost========================================
	//매개변수로 id 받는거맞겟지
	public BoardDTO thisPost(int id) {
		System.out.println("DAO))===Board DAO thisPost (게시글 한개 검색) 출력 확인===");
		BoardDTO bdto = new BoardDTO();
		String sql = "SELECT * FROM board WHERE id=?";
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				){
			//실행문 next쓰는거 무조건 물어봐줘야한다고했으니까 
			// if로 한번 물어보기 근데 그럼 ResultSet에 담아야하나
			if(rs.next()) {
				bdto.setId(rs.getInt("id"));
				bdto.setTitle(rs.getString("title"));
				bdto.setContent(rs.getString("content"));
				bdto.setWriter(rs.getString("writer"));
				bdto.setCreatedAt(rs.getString("createdAt"));
				//배열이 아니고 한개라서 배열에 add로 담을필요가없음
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return bdto;
	}
	
	//게시물 수정하는 쿼리 -- modPost========================================
	public int modPost(BoardDTO bdto) {
		System.out.println("DAO))===Board DAO modPost (게시글 수정) 출력 확인===");
		int result = 0;
		String sql = "UPDATE board SET title=?,content=? WHERE id=?";
		
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			pstmt.setString(1, bdto.getTitle());
			pstmt.setString(2, bdto.getContent());
			pstmt.setInt(3, bdto.getId());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//게시물 삭제하는 쿼리 -- delPost========================================
	public int delPost(int id) {
		System.out.println("DAO))===Board DAO delPost (게시글 삭제) 출력 확인===");
		int result = 0; 
		String sql = "DELETE FROM board WHERE id=?";
		
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			//DELETE 문이니까 executeUpdate();
			//여기서도  sql문에 ? 있으니까 대응 먼저
			//난 id를 int로 줫는데 매개변수를 
			pstmt.setInt(1, id);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}

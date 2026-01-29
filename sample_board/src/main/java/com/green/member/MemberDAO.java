package com.green.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.board.BoardDTO;
import com.mysql.cj.protocol.Resultset;


@Repository
public class MemberDAO {

	@Autowired
	private DataSource dataSource;
	
	//전체 회원 목록 뽑아낼거
	public List<MemberDTO> allUser(){
		System.out.println("333멤버DAO 전체회원목록메소드 allUser");
		String sql = "SELECT * FROM user";
		List<MemberDTO> userList = new ArrayList<MemberDTO>();
		try(
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberDTO mdto = new MemberDTO();
				mdto.setNum(rs.getInt("num"));
				mdto.setId(rs.getString("id"));
				mdto.setPw(rs.getString("pw"));
				mdto.setName(rs.getString("name"));
				mdto.setReg_date(rs.getString("reg_date"));
				userList.add(mdto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
	
	//회원 추가 하는거
	public int addUser(MemberDTO mdto) {
		System.out.println("333멤버DAO 회원추가메소드 addUser");
		String sql = "INSERT INTO user(id, pw, name) VALUES(?,?,?)";
		int result = 0;
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			//물음표 대응 먼저 하고 executeUpdate
			pstmt.setString(1,mdto.getId());
			pstmt.setString(2,mdto.getPw());
			pstmt.setString(3,mdto.getName());
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//한사람만 출력하는거
	public MemberDTO oneUser(String id) {
		System.out.println("333멤버DAO 회원한명메소드 oneUser");
		MemberDTO mdto = new MemberDTO();
		String sql = "SELECT * FROM user WHERE id=?";
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			pstmt.setString(1, mdto.getId());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				mdto.setNum(rs.getInt("num"));
				mdto.setId(rs.getString("id"));
				mdto.setPw(rs.getString("pw"));
				mdto.setName(rs.getString("name"));
				mdto.setReg_date(rs.getString("reg_date"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mdto;
	}
	
	
	//회원 정보 수정하기
	public int updateUser(MemberDTO mdto) {
		System.out.println("333멤버DAO 회원수정메소드 updateUser");
		int result = 0;
		String sql = "UPDATE user SET name=?";
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			//이것도 물음표대응 먼저하기
			pstmt.setString(1,mdto.getName());
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//회원 정보 삭제하기
	public int deleteUser(String id) {
		System.out.println("333멤버DAO 회원삭제메소드 deleteUser");
		int result = 0;
		String sql = "DELETE FROM user WHERE id=?";
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}

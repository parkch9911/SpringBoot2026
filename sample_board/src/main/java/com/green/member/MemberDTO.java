package com.green.member;

public class MemberDTO {
	private int num; //아이디 고유번호(pk)
	private String id; //유저id 
	private String pw; //유저pw
	private String name; //유저 이름
	private String reg_date; // 계정 생성일
	
	//get set---
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	
	
}

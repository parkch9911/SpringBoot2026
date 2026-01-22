package com.green.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.book.dao.BookDAO;
import com.green.book.dto.BookDTO;


//서비스에서 비지니스 로직 들어가는 부분
//도서리스트 map에 있는 것들을 인풋값이랑 비교해서 해당 도서가 대여가능한지 안한지 알 수 있게.

@Service
public class BookService {
	
	@Autowired
	BookDAO bookDAO;
	
	//책추가
	public void addComplete(BookDTO bdto) {
		bookDAO.addnewBook(bdto);
	}
	//책대여
	public void rentComplete(BookDTO bdto) {
		BookDTO selected = bookDAO.selectBook(bdto);
		//도서명이 존재하면 
		if(selected != null) {
			System.out.println("대여신청이 완료되었습니다.");
			System.out.println("대여 도서명 : "+selected.getTitle());
			System.out.println("대여 도서번호 : "+selected.getIsbn());
		}else {
			System.out.println("없는 도서입니다.");
		}
	}
}

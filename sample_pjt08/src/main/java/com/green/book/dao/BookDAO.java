package com.green.book.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.green.book.dto.BookDTO;


//여기가 저장소 쿼리문
// 도서add, select 문 여기에
@Repository
public class BookDAO {
	private Map<String, BookDTO> bookDB = new HashMap<String,BookDTO>();
	
	//도서추가
	public void addnewBook(BookDTO bdto) {
		//책의 키는 제목  // value는 정보들
		bookDB.put(bdto.getTitle(),bdto);
		printBookList();
	}
	
	//도서 select
	public BookDTO selectBook(BookDTO bdto) {
		BookDTO selected = bookDB.get(bdto.getTitle());
		return selected;
	}
	
	//도서목록 출력문
	public void printBookList() {
		for(String title : bookDB.keySet()) {
			BookDTO bdto = bookDB.get(title);
			System.out.println("추가 된 도서");
			System.out.println("도서명 : "+bdto.getTitle());
			System.out.println("저자명 : "+bdto.getAuthor());
			System.out.println("도서번호 : "+bdto.getIsbn());
		}
	}
}

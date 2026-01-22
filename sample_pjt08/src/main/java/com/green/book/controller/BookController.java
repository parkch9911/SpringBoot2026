package com.green.book.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.green.book.dto.BookDTO;
import com.green.book.service.BookService;

@Controller
public class BookController {
	
	@Autowired
	BookService bookService;
	
	//메인
	@GetMapping("/book/home")
	public String bookmain() {
		return "bookmain";
	}
	
	//책 추가 //회원가입
	@GetMapping("/book/add")
	public ModelAndView bookadd(BookDTO bdto) {
		ModelAndView mv = new ModelAndView();
		bookService.addComplete(bdto);
		
		mv.addObject("author", bdto.getAuthor());
		mv.addObject("title", bdto.getTitle());
		mv.addObject("isbn", bdto.getIsbn());
		mv.setViewName("bookadd");
		return mv;
	}
	
	//=======================================
	
	//책 대여 //로그인
		@GetMapping("/book/rent")
		public String bookrent() {
			return "bookrent";
		}
		
	//책 대여정보 //로그인성공
	@GetMapping("/book/rent/result")
	public ModelAndView bookrentresult(BookDTO bdto) {
		
		bookService.rentComplete(bdto);
		ModelAndView mv = new ModelAndView();
		LocalDate today = LocalDate.now();
		LocalDate next = today.plusWeeks(2);
		
		mv.addObject("new_date", today);
		mv.addObject("new_back", next);
		mv.addObject("new_title",bdto.getTitle());
		mv.addObject("new_author",bdto.getAuthor());
		mv.addObject("new_isbn",bdto.getIsbn());
		mv.setViewName("rentresult");
		return mv;
	}
}

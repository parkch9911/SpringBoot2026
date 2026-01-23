package com.green.book.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.green.book.dto.BookDTO;
import com.green.book.service.BookService;

@Controller
public class BookController {
	List<BookDTO> booklist = new ArrayList<BookDTO>();
	
	@Autowired
	BookService bookService;
	
	//메인  //그냥 단순하게 링크타는용
	@GetMapping("/book/home")
	public String bookmain(Model model,BookDTO bdto) {
		bookService.addComplete(bdto);
		booklist.add(bdto);
		model.addAttribute("list",booklist);
		return "bookmain";
	}
	
	//책 추가
	@GetMapping("/book/add")
	public String bookadd() {
		//이게 책 추가하는거 화면출력으로 어떤도서를 추가했는지를 안보내줄거면 model필요없이 되는거아닌가 
		
		return "bookadd";
	}
	
	//책 대여 //로그인
		@GetMapping("/book/rent")
		public String bookrent(BookDTO bdto) {
			
			return "bookrent";
		}
		
	//책 대여결과화면 //로그인성공
	@GetMapping("/book/rent/result")
	public ModelAndView bookrentresult(BookDTO bdto,
			@RequestParam("name") String name
			) {
		//이건 그냥 대여한다는 책이 있는지 확인하는거 
		bookService.rentComplete(bdto);
		ModelAndView mv = new ModelAndView();
		LocalDate today = LocalDate.now();
		LocalDate next = today.plusWeeks(2);
		
		mv.addObject("new_date", today);
		mv.addObject("new_back", next);
		mv.addObject("new_name", name);
		mv.addObject("new_title",bdto.getTitle());
		mv.addObject("new_author",bdto.getAuthor());
		mv.addObject("new_isbn",bdto.getIsbn());
		mv.setViewName("rentresuslt");
		return mv;
	}
}

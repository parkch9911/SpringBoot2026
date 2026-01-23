package com.green;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddressController {
	//heap메모리에 주소 데이터를 담을 리스트가 필요
	//ArrayList<E>
	private List<AddressDTO> addressList = new ArrayList<AddressDTO>();
	
	//1. 주소록 목록 화면
	@GetMapping("/address")
	public String list(Model model) {
		model.addAttribute("list",addressList);
		return "addressList2";
	}
	
	//2. 주소 등록화면
	@PostMapping("/add-address")
	public String addr(AddressDTO adto) {
		//ArrayList 삽입
		//삽입메소드 : add(value) 
		addressList.add(adto);
		//현재 url을 add-address 에서 address로 이동
		return "redirect:/address";
	}
}

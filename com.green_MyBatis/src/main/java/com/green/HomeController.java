package com.green;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.green.carproduct.CarProductDTO;
import com.green.carproduct.CarProductService;

@Controller
public class HomeController {
	
	@Autowired
	CarProductService carProductService;
	
    //http://localhost:8090, 또는 http://localhost:8090/
	@GetMapping({"","/"})
	public String home(Model model) {
		System.out.println("HomeController 확인");
		//List<CarProductDTO>
		//carProductService.getAllCarProduct()의 역할은 
		// 키 밸류값을 들고옴 DB에서 꺼내와 List 라는 ArrayList 배열에 저장
		List<CarProductDTO> carlist = carProductService.getAllCarProduct();
		
		//모델로 담아서 home.html로 내보낸디.
		//단 , 모델은 한번 담아보내면 다른 페이지로 이동해도 자료를 가지고 갈 수 없다.
		model.addAttribute("carlist", carlist);
		return "home";
	}
}

package com.green.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AcademyController {

	
	@GetMapping("/academy/enroll")
	public String enroll() {
		return "enroll";
	}
	
	@PostMapping("/academy/enrollResult")
	public ModelAndView result(
			@RequestParam("name") String name,
			@RequestParam("phone") String phone,
			@RequestParam("subject") String subject,
			@RequestParam("periods") int periods
			) {
		ModelAndView mv = new ModelAndView();
		LocalDate today = LocalDate.now();
		LocalDate next = today.plusDays(10);
		
		
		mv.addObject("new_days", next);
		mv.addObject("new_name", name);
		mv.addObject("new_phone",phone);
		mv.addObject("new_subject",subject);
		mv.addObject("new_pay",100000*periods);
		mv.addObject("new_period", periods);
		mv.setViewName("result");
		return mv;
	}

}

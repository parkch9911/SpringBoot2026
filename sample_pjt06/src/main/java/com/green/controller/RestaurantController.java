package com.green.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RestaurantController {

	@GetMapping("/restaurant/booking")
	public String Book() {
		return "order";
	}
	
	@PostMapping("/restaurant/bookingComplete")
	public ModelAndView Complete(
			@RequestParam("name") String name,
			@RequestParam("phone") String phone,
			@RequestParam("date") String date,
			@RequestParam("time") String time,
			@RequestParam("quantity") int quantity
			
			) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("new_name", name);
		mv.addObject("new_phone", phone);
		mv.addObject("new_date", date);
		mv.addObject("new_time", time);
		mv.addObject("new_quantity", quantity);
		mv.setViewName("complete");
		return mv;
	}
	
}

package com.green.carproduct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.carproduct.mapper.CarProductMapper;

@Service
public class CarProductService {
	
	//CarProductMapper을 @Autowired 로 의존객체삽입
	@Autowired
	CarProductMapper carProductMapper;
	
	//메소드는 CarProductMapper의 인터페이스 복붙
	public List<CarProductDTO> getAllCarProduct(){	
		return carProductMapper.getAllCarProduct();
	}
}

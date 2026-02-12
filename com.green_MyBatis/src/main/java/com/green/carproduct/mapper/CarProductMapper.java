package com.green.carproduct.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.green.carproduct.CarProductDTO;

@Mapper
public interface CarProductMapper {
	
	//추상 메서드
	//carProduct 모두 출력(검색)
	// 이 메소드는 xml에 연결하여 select SQL 작성해야한다.
	public List<CarProductDTO> getAllCarProduct();
	
}

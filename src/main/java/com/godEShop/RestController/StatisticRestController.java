package com.godEShop.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.godEShop.Dao.ProductDAO;
import com.godEShop.Dto.ProductStatisticDto;

@CrossOrigin("*")
@RestController
public class StatisticRestController {
	@Autowired
	ProductDAO prodDAO;
	
	@GetMapping("/rest/statistic/products")
	public List<ProductStatisticDto> getProductsStatistic() {
		return prodDAO.getProductStatistic();
	}
}

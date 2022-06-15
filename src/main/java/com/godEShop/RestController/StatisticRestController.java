package com.godEShop.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.godEShop.Dao.ProductDAO;
import com.godEShop.Dto.ProductImageDto;
import com.godEShop.Dto.ProductStatisticDto;
import com.godEShop.Dto.ProductsStatisticDto;

@CrossOrigin("*")
@RestController
public class StatisticRestController {
	@Autowired
	ProductDAO prodDAO;
	
	@GetMapping("/rest/statistic/products")
	public List<ProductsStatisticDto> getProductsStatistic() {
		return prodDAO.getProductStatistic();
	}
	
	@GetMapping("/rest/products/image")
	public List<ProductImageDto> getProductImage() {
		return prodDAO.getProductImage();
	}
	
	@GetMapping("/rest/statistic/products/{name}")
	public List<ProductsStatisticDto> get1ProductsStatistic(@PathVariable("name") String name) {
		return prodDAO.get1ProductStatistic("%" + name + "%");
	}
	
	@GetMapping("/rest/products/image/{name}")
	public List<ProductImageDto> get1ProductImage(@PathVariable("name") String name) {
		return prodDAO.get1ProductImage("%" + name + "%");
	}
	
	@GetMapping("/rest/products/singleimage/{id}")
	public List<ProductImageDto> getSingleProductImage(@PathVariable("id") Long id) {
		return prodDAO.getSingleProductImage(id);
	}
	
	@GetMapping("/rest/statistic/product/{id}")
	public List<ProductStatisticDto> get1ProductStatistic(@PathVariable("id") Long id) {
		return prodDAO.get1ProductStatistic(id);
	}
}

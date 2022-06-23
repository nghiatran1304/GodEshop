package com.godEShop.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.godEShop.Dao.OrderDAO;
import com.godEShop.Dao.ProductDAO;
import com.godEShop.Dao.UserDAO;
import com.godEShop.Dto.ProductImageDto;
import com.godEShop.Dto.ProductStatisticDto;
import com.godEShop.Dto.ProductsStatisticDto;
import com.godEShop.Dto.UserStatisticDto;
import com.godEShop.Dto.UsersStatisticDto;

@CrossOrigin("*")
@RestController
public class StatisticRestController {
	@Autowired
	ProductDAO prodDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	OrderDAO orderDAO;
	
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
	
	@GetMapping("/rest/statistic/users")
	public List<UsersStatisticDto> getUsersStatistic() {
		return userDAO.getAllUserStat();
	}
	
	@GetMapping("/rest/statistic/user/{id}")
	public List<UserStatisticDto> getSingleUserStatistic(@PathVariable("id") int id) {
		return userDAO.get1UserStat(id);
	}

	@GetMapping("/rest/statistic/find1User/{username}")
	public List<UsersStatisticDto> find1UserByUsername(@PathVariable("username") String username) {
		return userDAO.find1UserStat("%" + username + "%");
	}
	
	@GetMapping("/rest/statistic/totalAccount")
	public int getTotalAccount() {
		return userDAO.getTotalAccount();
	}
	
	@GetMapping("/rest/statistic/totalMaleAccount")
	public int getTotalMaleAccount() {
		return userDAO.getTotalAccountByMale();
	}
	
	@GetMapping("/rest/statistic/totalAccountOrdered")
	public int getTotalAccountOrdered() {
		return userDAO.getTotalAccountOrdered();
	}
	
	@GetMapping("/rest/statistic/totalOrder")
	public int getTotalOrder() {
		return orderDAO.getTotalOrder();
	}
	
	@GetMapping("/rest/statistic/totalPendingOrder")
	public int getTotalPendingOrder() {
		return orderDAO.getTotalPendingOrder();
	}
	
	@GetMapping("/rest/statistic/totalConfirmOrder")
	public int getTotalConfirmOrder() {
		return orderDAO.getTotalConfirmOrder();
	}
	
	@GetMapping("/rest/statistic/totalDeliveryOrder")
	public int getTotalDeliveryOrder() {
		return orderDAO.getTotalDeliveryOrder();
	}
	
	@GetMapping("/rest/statistic/totalCompleteOrder")
	public int getTotalCompleteOrder() {
		return orderDAO.getTotalCompleteOrder();
	}
	
	@GetMapping("/rest/statistic/productsByMale/{gender}")
	public List<ProductsStatisticDto> getProductsStatisticByMale(@PathVariable("gender") int gender) {
		return prodDAO.getBestProductStatisticByMale(gender);
	}
	
}

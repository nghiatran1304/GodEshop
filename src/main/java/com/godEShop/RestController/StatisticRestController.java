package com.godEShop.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.godEShop.Dto.UserOrderedStatisticDto;
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
	
	@GetMapping("/rest/statistic/productsByTime/start={dateStart}&end={dateEnd}")
	public List<ProductsStatisticDto> getProductsStatisticByTime(@PathVariable("dateStart") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart,
			@PathVariable("dateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd) throws ParseException {
		return prodDAO.getProductStatisticByTime(dateStart, dateEnd);
	}
	
	@GetMapping("/rest/products/image")
	public List<ProductImageDto> getProductImage() throws Exception{
		return prodDAO.getProductImage();
	}
	
	@GetMapping("/rest/statistic/products/{name}")
	public List<ProductsStatisticDto> get1ProductsStatistic(@PathVariable("name") String name) {
		return prodDAO.get1ProductStatistic("%" + name + "%");
	}
	
	@GetMapping("/rest/statistic/productsByTime/{name}/start={dateStart}&end={dateEnd}")
	public List<ProductsStatisticDto> get1ProductsStatisticByTime(@PathVariable("name") String name,
			@PathVariable("dateStart") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart, 
			@PathVariable("dateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd) {
		return prodDAO.get1ProductStatisticByTime("%" + name + "%", dateStart, dateEnd);
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
	
	@GetMapping("/rest/statistic/productByTime/{id}/start={dateStart}&end={dateEnd}")
	public List<ProductStatisticDto> get1ProductStatisticByTime(@PathVariable("id") Long id, 
			@PathVariable("dateStart") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart, 
			@PathVariable("dateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd) {
		return prodDAO.get1ProductStatisticByTime(id, dateStart, dateEnd);
	}
	
	@GetMapping("/rest/statistic/users")
	public List<UsersStatisticDto> getUsersStatistic() {
		return userDAO.getAllUserStat();
	}
	
	@GetMapping("/rest/statistic/usersByTime/start={dateStart}&end={dateEnd}")
	public List<UsersStatisticDto> getUsersStatisticByTime(@PathVariable("dateStart") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart, 
			@PathVariable("dateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd) {
		return userDAO.getAllUserStatByTime(dateStart, dateEnd);
	}
	
	@GetMapping("/rest/statistic/user/{id}")
	public List<UserStatisticDto> getSingleUserStatistic(@PathVariable("id") int id) {
		return userDAO.get1UserStat(id);
	}
	
	@GetMapping("/rest/statistic/userByTime/{id}/start={dateStart}&end={dateEnd}")
	public List<UserStatisticDto> getSingleUserStatisticByTime(@PathVariable("id") int id,
			@PathVariable("dateStart") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart, 
			@PathVariable("dateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd) {
		return userDAO.get1UserStatByTime(id, dateStart, dateEnd);
	}

	@GetMapping("/rest/statistic/find1User/{username}")
	public List<UsersStatisticDto> find1UserByUsername(@PathVariable("username") String username) {
		return userDAO.find1UserStat("%" + username + "%");
	}
	
	@GetMapping("/rest/statistic/find1UserByTime/{username}/start={dateStart}&end={dateEnd}")
	public List<UsersStatisticDto> find1UserByUsernameByTime(@PathVariable("username") String username,
			@PathVariable("dateStart") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart, 
			@PathVariable("dateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd) {
		return userDAO.find1UserStatByTime("%" + username + "%", dateStart, dateEnd);
	}
	
	@GetMapping("/rest/statistic/totalAccount")
	public int getTotalAccount() {
		return userDAO.getTotalAccount();
	}
	
	@GetMapping("/rest/statistic/totalAccountOrdered")
	public int getTotalAccountOrdered() {
		List<UserOrderedStatisticDto> data = userDAO.getTotalAccountOrdered();
		HashMap<Integer, String> dataHandler = new HashMap<Integer, String>();
		for (int i = 0; i < data.size(); i++) {
			dataHandler.put(data.get(i).getId(), data.get(i).getUsername());
		}
		List<UserOrderedStatisticDto> dataHandled = new ArrayList<>();
		for (int i = 0; i < dataHandler.size(); i++) {
			dataHandled.add(new UserOrderedStatisticDto());
		}
		return dataHandled.size();
	}
	
	@GetMapping("/rest/statistic/totalAccountOrderedByTime/start={dateStart}&end={dateEnd}")
	public int getTotalAccountOrderedByTime(@PathVariable("dateStart") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart, 
			@PathVariable("dateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd) {
		List<UserOrderedStatisticDto> data = userDAO.getTotalAccountOrderedByTime(dateStart, dateEnd);
		HashMap<Integer, String> dataHandler = new HashMap<Integer, String>();
		for (int i = 0; i < data.size(); i++) {
			dataHandler.put(data.get(i).getId(), data.get(i).getUsername());
		}
		List<UserOrderedStatisticDto> dataHandled = new ArrayList<>();
		for (int i = 0; i < dataHandler.size(); i++) {
			dataHandled.add(new UserOrderedStatisticDto());
		}
		return dataHandled.size();
	}
	
	@GetMapping("/rest/statistic/totalOrder")
	public int getTotalOrder() {
		return orderDAO.getTotalOrder();
	}
	
	@GetMapping("/rest/statistic/totalOrderByTime/start={dateStart}&end={dateEnd}")
	public int getTotalOrderByTime(@PathVariable("dateStart") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart, 
			@PathVariable("dateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd) {
		return orderDAO.getTotalOrderByTime(dateStart, dateEnd);
	}
	
	@GetMapping("/rest/statistic/totalPendingOrder")
	public int getTotalPendingOrder() {
		return orderDAO.getTotalPendingOrder();
	}
	
	@GetMapping("/rest/statistic/totalPendingOrderByTime/start={dateStart}&end={dateEnd}")
	public int getTotalPendingOrderByTime(@PathVariable("dateStart") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart, 
			@PathVariable("dateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd) {
		return orderDAO.getTotalPendingOrderByTime(dateStart, dateEnd);
	}
	
	@GetMapping("/rest/statistic/totalConfirmOrder")
	public int getTotalConfirmOrder() {
		return orderDAO.getTotalConfirmOrder();
	}
	
	@GetMapping("/rest/statistic/totalConfirmOrderByTime/start={dateStart}&end={dateEnd}")
	public int getTotalConfirmOrderByTime(@PathVariable("dateStart") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart, 
			@PathVariable("dateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd) {
		return orderDAO.getTotalConfirmOrderByTime(dateStart, dateEnd);
	}
	
	@GetMapping("/rest/statistic/totalDeliveryOrder")
	public int getTotalDeliveryOrder() {
		return orderDAO.getTotalDeliveryOrder();
	}
	
	@GetMapping("/rest/statistic/totalDeliveryOrderByTime/start={dateStart}&end={dateEnd}")
	public int getTotalDeliveryOrderByTime(@PathVariable("dateStart") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart, 
			@PathVariable("dateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd) {
		return orderDAO.getTotalDeliveryOrderByTime(dateStart, dateEnd);
	}
	
	@GetMapping("/rest/statistic/totalCompleteOrder")
	public int getTotalCompleteOrder() {
		return orderDAO.getTotalCompleteOrder();
	}
	
	@GetMapping("/rest/statistic/totalCompleteOrderByTime/start={dateStart}&end={dateEnd}")
	public int getTotalCompleteOrderByTime(@PathVariable("dateStart") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart, 
			@PathVariable("dateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd) {
		return orderDAO.getTotalCompleteOrderByTime(dateStart, dateEnd);
	}
	
	@GetMapping("/rest/statistic/productsByMale/{gender}")
	public List<ProductsStatisticDto> getProductsStatisticByMale(@PathVariable("gender") int gender) {
		return prodDAO.getBestProductStatisticByMale(gender);
	}
	
	@GetMapping("/rest/statistic/productsByMaleByTime/{gender}/start={dateStart}&end={dateEnd}")
	public List<ProductsStatisticDto> getProductsStatisticByMaleByTime(@PathVariable("gender") int gender,
			@PathVariable("dateStart") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart, 
			@PathVariable("dateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd) {
		return prodDAO.getBestProductStatisticByMaleByTime(gender, dateStart, dateEnd);
	}
	
}

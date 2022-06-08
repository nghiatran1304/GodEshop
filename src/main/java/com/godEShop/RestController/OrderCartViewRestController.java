package com.godEShop.RestController;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


import com.godEShop.Dto.OrderCartViewDto;
import com.godEShop.Dto.OrderListDto;
import com.godEShop.Entity.Order;
import com.godEShop.Service.OrderDetailService;
import com.godEShop.Service.OrderService;

@CrossOrigin("*")
@RestController
public class OrderCartViewRestController {

    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    OrderService orderService;

    
    @GetMapping("/rest/orderCartViewDto/{id}")
    public List<OrderCartViewDto> lstOrderCartViewDto(@PathVariable("id") Long id){
	return orderDetailService.findByIdOrderCartViewDto(id);
    }
    
    @GetMapping("/rest/orderListDto/{username}")
    public List<OrderListDto> lstOrderDto(HttpServletRequest request){
    	String username = request.getRemoteUser();
	return orderService.findByUsername1(username);
	
	
	
    }
    @PutMapping("/rest/order-update-cancel/{id}")
 	public Order cancelOrder(@PathVariable("id") Long id) {
    	Order order = orderService.findById(id);
    	orderService.updateCancel(order);
	 return order;
 }
   

}
































































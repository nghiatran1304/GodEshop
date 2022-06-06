package com.godEShop.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.godEShop.Dto.OrderInfoDto;
import com.godEShop.Entity.Order;
import com.godEShop.Service.OrderService;

@CrossOrigin("*")
@RestController
public class OrderRestController {

    @Autowired
    OrderService orderService;

    @PostMapping("/rest/orders")
    public Order create(@RequestBody JsonNode orderData) {
	return orderService.create(orderData);
    }
    
    @GetMapping("/rest/allOrders")
    public List<Order> allOrder(){
	return orderService.findAllOrders();
    }
    
    @GetMapping("/rest/order-pending")
    public List<Order> orderPending(){
	return orderService.findAllOrderPending();
    }
    
    @GetMapping("/rest/order-confirmed")
    public List<Order> orderConfirm(){
	return orderService.findAllOrderConfirmed();
    }
    
    @GetMapping("/rest/order-delivery")
    public List<Order> orderDelivery(){
	return orderService.findAllOrderDelivery();
    }
    
    @GetMapping("/rest/order-success")
    public List<Order> orderSuccess(){
	return orderService.findAllOrderSuccess();
    }
    
    @GetMapping("/rest/order-cancel")
    public List<Order> orderCancel(){
	return orderService.findAllOrderCancel();
    }
    
    @GetMapping("/rest/order-infoDto/{id}")
    public List<OrderInfoDto> lstOrderDto(@PathVariable("id") Long id){
	return orderService.findAllOrderInfoDto(id);
    }
    
    @PutMapping("/rest/order-update-confirm/{id}")
    public Order updateConfirm(@PathVariable("id") Long id, @RequestBody OrderInfoDto o) {
	Order order = orderService.findById(id);
	return orderService.updateConfirm(order);
    }
    
    @PutMapping("/rest/order-update-delivery/{id}")
    public Order updateDelivery(@PathVariable("id") Long id,@RequestBody OrderInfoDto o) {
	Order order = orderService.findById(id);
	return orderService.updateDelivery(order);
    }
    
    @PutMapping("/rest/order-update-success/{id}")
    public Order updateSuccess(@PathVariable("id") Long id, @RequestBody OrderInfoDto o) {
	Order order = orderService.findById(id);
	return orderService.updateSuccess(order);
    }
    
}































































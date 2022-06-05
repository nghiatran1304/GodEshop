package com.godEShop.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
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
}

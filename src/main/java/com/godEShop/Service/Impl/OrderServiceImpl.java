package com.godEShop.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godEShop.Dao.OrderDAO;
import com.godEShop.Dao.OrderDetailDAO;
import com.godEShop.Dto.OrderListDto;
import com.godEShop.Entity.Order;
import com.godEShop.Entity.OrderDetail;
import com.godEShop.Service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Override
    public Order create(JsonNode orderData) {
	// TODO Auto-generated method stub
	ObjectMapper mapper = new ObjectMapper();
	Order order = mapper.convertValue(orderData, Order.class);
	orderDAO.save(order);
	TypeReference<List<OrderDetail>> type = new TypeReference<List<OrderDetail>>() {
	};
	List<OrderDetail> details = mapper.convertValue(orderData.get("orderDetails"), type).stream()
		.peek(d -> d.setOrder(order)).collect(Collectors.toList());
	orderDetailDAO.saveAll(details);
	return order;
    }

    @Override
    public Order findById(Long id) {
	// TODO Auto-generated method stub
	return orderDAO.findById(id).get();
    }

    @Override
    public List<Order> findAll() {
	// TODO Auto-generated method stub
	return orderDAO.findAll();
    }

    @Override
    public List<OrderListDto> findByUsername1(String username) {
	// TODO Auto-generated method stub
	return orderDAO.findByUsername1(username);
    }
}

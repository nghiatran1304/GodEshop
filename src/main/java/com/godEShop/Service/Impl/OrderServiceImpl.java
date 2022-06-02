package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.OrderDAO;
import com.godEShop.Dto.OrderListDto;
import com.godEShop.Entity.Order;
import com.godEShop.Service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderDAO orderDAO;

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
	public List<OrderListDto> findByUsername(String username) {
		// TODO Auto-generated method stub
		return orderDAO.findByUsername(username);
	}
}

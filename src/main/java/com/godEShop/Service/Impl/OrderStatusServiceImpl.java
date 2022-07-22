package com.godEShop.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.OrderStatusDAO;
import com.godEShop.Entity.OrderStatus;
import com.godEShop.Service.OrderStatusService;

@Service
public class OrderStatusServiceImpl implements OrderStatusService{

    @Autowired
    OrderStatusDAO osDAO;
    
    @Override
    public OrderStatus findById(int id) {
	// TODO Auto-generated method stub
	return osDAO.getById(id);
    }

}

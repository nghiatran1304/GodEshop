package com.godEShop.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.OrderDetailDAO;
import com.godEShop.Service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{

    @Autowired
    OrderDetailDAO orderDetailDAO;
    
    
}

package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.OrderMethodDAO;
import com.godEShop.Entity.OrderMethod;
import com.godEShop.Service.OrderMethodService;

@Service
public class OrderMethodServiceImpl implements OrderMethodService{

    @Autowired
    OrderMethodDAO omDAO;

    @Override
    public List<OrderMethod> lstOrderMethod() {
	// TODO Auto-generated method stub
	return omDAO.findAll();
    }

    @Override
    public OrderMethod getById(int id) {
	// TODO Auto-generated method stub
	return omDAO.getById(id);
    }
    
    
}

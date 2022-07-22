package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.OrderDetailDAO;
import com.godEShop.Dto.OrderCartViewDto;
import com.godEShop.Entity.OrderDetail;
import com.godEShop.Service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Override
    public List<OrderCartViewDto> findByIdOrderCartViewDto(Long id) {
	// TODO Auto-generated method stub
	return orderDetailDAO.findByIdOrderCartViewDto(id);
    }

    @Override
    public List<OrderDetail> findAllProductByOrderDetailId(Long id) {
	// TODO Auto-generated method stub
	return orderDetailDAO.findAllProductByOrderDetailId(id);
    }

}

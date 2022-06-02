package com.godEShop.Service;

import java.util.List;

import com.godEShop.Dto.OrderListDto;
import com.godEShop.Entity.Order;

public interface OrderService {

	Order findById(Long id);

	List<Order> findAll();

	List<OrderListDto> findByUsername(String username);

}

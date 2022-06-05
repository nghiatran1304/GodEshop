package com.godEShop.Service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.godEShop.Dto.OrderListDto;
import com.godEShop.Entity.Order;

public interface OrderService {


    Order create(JsonNode orderData);

    Order findById(Long id);

    List<Order> findAll();

    List<OrderListDto> findByUsername1(String username);

}

package com.godEShop.Service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.godEShop.Dto.OrderInfoDto;
import com.godEShop.Dto.OrderListDto;
import com.godEShop.Entity.Order;

public interface OrderService {

    Order create(JsonNode orderData);

    Order findById(Long id);

    List<Order> findAll();

    List<OrderListDto> findByUsername1(String username);

    List<Order> findAllOrderPending();

    List<Order> findAllOrderConfirmed();

    List<Order> findAllOrderDelivery();

    List<Order> findAllOrderSuccess();

    List<Order> findAllOrderCancel();

    List<Order> findAllOrders();

    List<OrderInfoDto> findAllOrderInfoDto(Long id);

}
